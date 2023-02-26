package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelTypeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderCancel;
import com.gofield.domain.rds.domain.order.projection.OrderCancelInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderChangeInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderReturnInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderShippingInfoAdminProjectionResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.gofield.domain.rds.domain.item.QShippingTemplate.shippingTemplate;
import static com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag.ORDER_CHANGE_REQUEST;
import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderCancel.orderCancel;
import static com.gofield.domain.rds.domain.order.QOrderCancelComment.orderCancelComment;
import static com.gofield.domain.rds.domain.order.QOrderCancelItem.orderCancelItem;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;
import static com.gofield.domain.rds.domain.order.QOrderShippingAddress.orderShippingAddress;
import static com.gofield.domain.rds.domain.user.QUser.user;

@RequiredArgsConstructor
public class OrderCancelRepositoryCustomImpl implements OrderCancelRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression containKeyword(String keyword){
        if (keyword == null || keyword.equals("")) {
            return null;
        }
        return orderCancel.order.orderNumber.contains(keyword);
    }

    private BooleanExpression eqStatus(EOrderCancelStatusFlag status){
        if(status==null){
            return null;
        }
        return orderCancel.status.eq(status);
    }


    @Override
    public Page<OrderCancel> findAllFetchJoin(Long userId, Pageable pageable) {
        List<OrderCancel> result = jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order).fetchJoin()
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancelComment.user.id.eq(userId))
                .orderBy(orderCancel.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if(result.isEmpty()){
            return new PageImpl<>(result, pageable, 0);
        }

        List<Long> totalCount = jpaQueryFactory
                .select(orderCancelComment.id)
                .from(orderCancel)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment)
                .where(orderCancelComment.user.id.eq(userId))
                .fetch();

        return new PageImpl<>(result, pageable, totalCount.size());
    }

    @Override
    public OrderCancel findByCancelIdAndUserIdFetchJoin(Long cancelId, Long userId) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order).fetchJoin()
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .leftJoin(orderCancel.shippingTemplate, shippingTemplate).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.id.eq(cancelId), orderCancelComment.user.id.eq(userId))
                .fetchFirst();
    }

    @Override
    public OrderCancel findByCancelIdAndUserIdFetchComment(Long cancelId, Long userId) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .where(orderCancel.id.eq(cancelId) ,orderCancelComment.user.id.eq(userId))
                .fetchFirst();
    }

    @Override
    public OrderCancel findByCancelIdFetchJoin(Long cancelId) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order).fetchJoin()
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .leftJoin(orderCancel.shippingTemplate, shippingTemplate).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.id.eq(cancelId))
                .fetchFirst();
    }

    @Override
    public OrderCancel findByShippingIdAndStatusReturn(Long orderShippingId) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancel.orderShipping, orderShipping).fetchJoin()
                .where(orderCancel.orderShipping.id.eq(orderShippingId), orderCancel.orderShipping.status.eq(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN).or(orderCancel.orderShipping.status.eq(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)))
                .orderBy(orderCancel.createDate.desc())
                .fetchFirst();
    }

    @Override
    public List<OrderCancel> findAllOrderCancelByKeyword(String keyword, EOrderCancelStatusFlag status) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order)
                .innerJoin(order.shippingAddress, orderShippingAddress)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CANCEL), containKeyword(keyword), eqStatus(status))
                .orderBy(orderCancel.id.desc())
                .fetch();
    }

    @Override
    public List<OrderCancel> findAllOrderChangeByKeyword(String keyword, EOrderCancelStatusFlag status) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order)
                .innerJoin(order.shippingAddress, orderShippingAddress)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CHANGE),
                        containKeyword(keyword), eqStatus(status))
                .orderBy(orderCancel.id.desc())
                .fetch();
    }



    @Override
    public OrderCancelInfoAdminProjectionResponse findAllOrderCancelByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable) {
        List<OrderCancel> content = jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order)
                .innerJoin(order.shippingAddress, orderShippingAddress)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CANCEL),
                        containKeyword(keyword), eqStatus(status))
                .orderBy(orderCancel.status.asc(), orderCancel.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(orderCancel.id)
                .from(orderCancel)
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CANCEL),
                        containKeyword(keyword), eqStatus(status))
                .fetch();

        List<EOrderCancelStatusFlag> allCount = jpaQueryFactory
                .select(orderCancel.status)
                .from(orderCancel)
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CANCEL))
                .fetch();

        Long receiptCount = allCount.stream().filter(p-> p.equals(EOrderCancelStatusFlag.ORDER_CANCEL_REQUEST)).collect(Collectors.counting());
        Long refuseCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CANCEL_DENIED)).collect(Collectors.counting());
        Long completeCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)).collect(Collectors.counting());
        if(content.isEmpty()){
            return OrderCancelInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, 0), allCount.size(), receiptCount, refuseCount, completeCount);
        }
        return OrderCancelInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, totalCount.size()), allCount.size(), receiptCount, refuseCount, completeCount);
    }

    @Override
    public OrderChangeInfoAdminProjectionResponse findAllOrderChangeByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable) {
        List<OrderCancel> content = jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order)
                .innerJoin(order.shippingAddress, orderShippingAddress)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CHANGE),
                        containKeyword(keyword), eqStatus(status))
                .orderBy(orderCancel.status.asc(), orderCancel.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(orderCancel.id)
                .from(orderCancel)
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CHANGE), containKeyword(keyword), eqStatus(status))
                .fetch();

        List<EOrderCancelStatusFlag> allCount = jpaQueryFactory
                .select(orderCancel.status)
                .from(orderCancel)
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.CHANGE))
                .fetch();

        Long receiptCount = allCount.stream().filter(p-> p.equals(EOrderCancelStatusFlag.ORDER_CHANGE_REQUEST)).collect(Collectors.counting());
        Long refuseCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CHANGE_DENIED)).collect(Collectors.counting());
        Long collectCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CHANGE_COLLECT_PROCESS)).collect(Collectors.counting());
        Long collectCompleteCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CHANGE_COLLECT_PROCESS_COMPLETE)).collect(Collectors.counting());
        Long reDeliveryCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CHANGE_REDELIVERY)).collect(Collectors.counting());
        Long completeCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_CHANGE_COMPLETE)).collect(Collectors.counting());
        if(content.isEmpty()){
            return OrderChangeInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, 0), allCount.size(), receiptCount, refuseCount, collectCount, collectCompleteCount, reDeliveryCount, completeCount);
        }
        return OrderChangeInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, totalCount.size()), allCount.size(), receiptCount, refuseCount, collectCount, collectCompleteCount, reDeliveryCount, completeCount);
    }

    @Override
    public OrderReturnInfoAdminProjectionResponse findAllOrderReturnByKeyword(String keyword, EOrderCancelStatusFlag status, Pageable pageable) {
        List<OrderCancel> content = jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order)
                .innerJoin(order.shippingAddress, orderShippingAddress)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.RETURN),
                        containKeyword(keyword), eqStatus(status))
                .orderBy(orderCancel.status.asc(), orderCancel.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(orderCancel.id)
                .from(orderCancel)
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.RETURN), containKeyword(keyword), eqStatus(status))
                .fetch();

        List<EOrderCancelStatusFlag> allCount = jpaQueryFactory
                .select(orderCancel.status)
                .from(orderCancel)
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.RETURN))
                .fetch();

        Long receiptCount = allCount.stream().filter(p-> p.equals(EOrderCancelStatusFlag.ORDER_RETURN_REQUEST)).collect(Collectors.counting());
        Long refuseCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_RETURN_DENIED)).collect(Collectors.counting());
        Long collectCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_RETURN_COLLECT_PROCESS)).collect(Collectors.counting());
        Long collectCompleteCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_RETURN_COLLECT_PROCESS_COMPLETE)).collect(Collectors.counting());
        Long completeCount = allCount.stream().filter(p -> p.equals(EOrderCancelStatusFlag.ORDER_RETURN_COMPLETE)).collect(Collectors.counting());
        if(content.isEmpty()){
            return OrderReturnInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, 0), allCount.size(), receiptCount, refuseCount, collectCount, collectCompleteCount, completeCount);
        }
        return OrderReturnInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, totalCount.size()), allCount.size(), receiptCount, refuseCount, collectCount, collectCompleteCount, completeCount);

    }

    @Override
    public List<OrderCancel> findAllOrderReturnByKeyword(String keyword, EOrderCancelStatusFlag status) {
        return jpaQueryFactory
                .selectFrom(orderCancel)
                .innerJoin(orderCancel.order, order)
                .innerJoin(order.shippingAddress, orderShippingAddress)
                .innerJoin(orderCancel.orderCancelComment, orderCancelComment).fetchJoin()
                .innerJoin(orderCancelComment.user, user).fetchJoin()
                .innerJoin(orderCancel.orderCancelItems, orderCancelItem).fetchJoin()
                .where(orderCancel.type.eq(EOrderCancelTypeFlag.RETURN),
                        containKeyword(keyword), eqStatus(status))
                .orderBy(orderCancel.id.desc())
                .fetch();
    }

}
