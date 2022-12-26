package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.item.projection.ItemInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderShipping;
import com.gofield.domain.rds.domain.order.projection.OrderShippingInfoAdminProjectionResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderItem.orderItem;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;
import static com.gofield.domain.rds.domain.order.QOrderShippingAddress.orderShippingAddress;

@RequiredArgsConstructor
public class OrderShippingRepositoryCustomImpl implements OrderShippingRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    private BooleanExpression containKeyword(String keyword){
        if (keyword == null || keyword.equals("")) {
            return null;
        }
        return orderShipping.shippingNumber.contains(keyword).or(orderShipping.orderNumber.contains(keyword));
    }

    private BooleanExpression eqStatus(EOrderShippingStatusFlag status){
        if(status==null){
            return null;
        }
        return orderShipping.status.eq(status);
    }


    @Override
    public OrderShipping findByShippingNumberAndOrderNumberFetchOrder(Long userId, String shippingNumber, String orderNumber) {
        return jpaQueryFactory
                .select(orderShipping)
                .from(orderShipping)
                .innerJoin(orderShipping.order, order).fetchJoin()
                .where(orderShipping.shippingNumber.eq(shippingNumber),
                        orderShipping.orderNumber.eq(orderNumber),
                        order.userId.eq(userId))
                .fetchFirst();
    }

    @Override
    public OrderShipping findByShippingNumberAndOrderNumberFetch(Long userId, String shippingNumber, String orderNumber) {
        return jpaQueryFactory
                .select(orderShipping)
                .from(orderShipping)
                .innerJoin(orderShipping.orderItems, orderItem).fetchJoin()
                .innerJoin(orderShipping.order, order).fetchJoin()
                .where(orderShipping.shippingNumber.eq(shippingNumber),
                        orderShipping.orderNumber.eq(orderNumber),
                        order.userId.eq(userId))
                .fetchFirst();
    }

    @Override
    public OrderShippingInfoAdminProjectionResponse findAllOrderShippingByKeyword(String keyword, EOrderShippingStatusFlag status, Pageable pageable) {
        List<OrderShipping> content = jpaQueryFactory
                .selectFrom(orderShipping)
                .innerJoin(orderShipping.orderItems, orderItem).fetchJoin()
                .innerJoin(orderItem.order, order).fetchJoin()
                .innerJoin(order.shippingAddress, orderShippingAddress).fetchJoin()
                .where(orderShipping.status.notIn(
                        EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL,
                        EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE,
                        EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE,
                        EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE,
                        EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE,
                        EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN,
                        EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE),
                        containKeyword(keyword), eqStatus(status))
                .orderBy(orderShipping.status.asc(), orderShipping.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(orderShipping.id)
                .from(orderShipping)
                .where(
                        orderShipping.status.notIn(
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL,
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE,
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE,
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE,
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE,
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN,
                                        EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE),
                        containKeyword(keyword), eqStatus(status))
                .fetch();

        List<EOrderShippingStatusFlag> allCount = jpaQueryFactory
                .select(orderShipping.status)
                .from(orderShipping)
                .where(
                        orderShipping.status.notIn(
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE))
                .fetch();

        Long receiptCount = allCount.stream().filter(p-> p.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)).collect(Collectors.counting());
        Long readyCount = allCount.stream().filter(p -> p.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)).collect(Collectors.counting());
        Long deliveryCount = allCount.stream().filter(p-> p.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY)).collect(Collectors.counting());
        Long deliveryCompleteCount = allCount.stream().filter(p-> p.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)).collect(Collectors.counting());
        Long cancelCompleteCount = allCount.stream().filter(p-> p.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE) || p.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)).collect(Collectors.counting());
        if(content.isEmpty()){
            return OrderShippingInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, 0), allCount.size(), receiptCount, readyCount, deliveryCount, deliveryCompleteCount, cancelCompleteCount);
        }
        return OrderShippingInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, totalCount.size()), allCount.size(), receiptCount, readyCount, deliveryCount, deliveryCompleteCount, cancelCompleteCount);
    }

    @Override
    public List<OrderShipping> findAllByKeyword(String keyword, EOrderShippingStatusFlag status) {
        return jpaQueryFactory
                .selectFrom(orderShipping)
                .innerJoin(orderShipping.order, order).fetchJoin()
                .innerJoin(order.shippingAddress, orderShippingAddress).fetchJoin()
                .innerJoin(orderShipping.orderItems, orderItem).fetchJoin()
                .where(containKeyword(keyword), eqStatus(status))
                .fetch();
    }

    @Override
    public OrderShipping findAdminOrderShippingById(Long id) {
        return jpaQueryFactory
                .selectFrom(orderShipping)
                .innerJoin(orderShipping.order, order).fetchJoin()
                .innerJoin(order.shippingAddress, orderShippingAddress).fetchJoin()
                .innerJoin(orderShipping.orderItems, orderItem).fetchJoin()
                .where(orderShipping.id.eq(id))
                .fetchOne();
    }

    @Override
    public OrderShipping findByIdFetchOrder(Long id) {
        return jpaQueryFactory
                .selectFrom(orderShipping)
                .innerJoin(orderShipping.order, order).fetchJoin()
                .where(orderShipping.id.eq(id))
                .fetchFirst();
    }

    @Override
    public OrderShipping findByIdNotFetch(Long id) {
        return jpaQueryFactory
                .selectFrom(orderShipping)
                .where(orderShipping.id.eq(id))
                .fetchFirst();
    }


}
