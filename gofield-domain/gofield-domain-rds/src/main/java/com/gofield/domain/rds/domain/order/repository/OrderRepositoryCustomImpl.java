package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;
import static com.gofield.domain.rds.domain.order.QOrderShippingAddress.orderShippingAddress;
import static com.gofield.domain.rds.domain.order.QOrderItem.orderItem;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Order findByOrderNumber(String orderNumber) {
        return jpaQueryFactory
                .select(order)
                .from(order)
                .innerJoin(orderShippingAddress)
                .on(order.shippingAddress.id.eq(orderShippingAddress.id))
                .innerJoin(order.orderShippings, orderShipping).fetchJoin()
                .where(orderShippingAddress.orderNumber.eq(orderNumber))
                .fetchOne();
    }
}
