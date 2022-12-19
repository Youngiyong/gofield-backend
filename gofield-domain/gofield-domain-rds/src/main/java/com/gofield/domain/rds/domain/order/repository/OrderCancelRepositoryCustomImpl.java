package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderCancel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QShippingTemplate.shippingTemplate;
import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderCancel.orderCancel;
import static com.gofield.domain.rds.domain.order.QOrderCancelComment.orderCancelComment;
import static com.gofield.domain.rds.domain.order.QOrderCancelItem.orderCancelItem;
import static com.gofield.domain.rds.domain.user.QUser.user;

@RequiredArgsConstructor
public class OrderCancelRepositoryCustomImpl implements OrderCancelRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OrderCancel> findAllFetchJoin(Long userId, Pageable pageable) {
        return jpaQueryFactory
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
    }

    @Override
    public OrderCancel findFetchJoin(Long cancelId, Long userId) {
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
}
