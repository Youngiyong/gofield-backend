package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderStatusFlag;
import com.gofield.domain.rds.domain.order.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;
import static com.gofield.domain.rds.domain.order.QOrderShippingAddress.orderShippingAddress;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Order findByOrderNumberAndUserIdAndNotStatusDelete(Long userId, String orderNumber) {
        return jpaQueryFactory
                .select(order)
                .from(order)
                .innerJoin(orderShippingAddress)
                .on(order.shippingAddress.id.eq(orderShippingAddress.id))
                .innerJoin(order.orderShippings, orderShipping).fetchJoin()
                .where(order.orderNumber.eq(orderNumber), order.userId.eq(userId), order.status.ne(EOrderStatusFlag.ORDER_DELETE), orderShipping.status.ne(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE))
                .fetchOne();
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        return jpaQueryFactory
                .select(order)
                .from(order)
                .where(order.orderNumber.eq(orderNumber))
                .fetchFirst();
    }

    @Override
    public List<Order> findAllByUserIdAndNotStatusDelete(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(order)
                .from(order)
                .innerJoin(orderShippingAddress)
                .on(order.shippingAddress.id.eq(orderShippingAddress.id))
                .innerJoin(order.orderShippings, orderShipping).fetchJoin()
                .where(order.userId.eq(userId), orderShipping.status.ne(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(order.id.desc())
                .fetch();
    }
}
