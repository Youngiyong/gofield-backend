package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderShipping;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.order.QOrder.order;
import static com.gofield.domain.rds.domain.order.QOrderItem.orderItem;
import static com.gofield.domain.rds.domain.order.QOrderShipping.orderShipping;
import static com.gofield.domain.rds.domain.order.QOrderShippingAddress.orderShippingAddress;

@RequiredArgsConstructor
public class OrderShippingRepositoryCustomImpl implements OrderShippingRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public OrderShipping findByShippingNumberAndOrderNumber(String shippingNumber, String orderNumber) {
        return jpaQueryFactory
                .select(orderShipping)
                .from(orderShipping)
                .innerJoin(orderShipping.orderItems, orderItem)
                .where(orderShipping.shippingNumber.eq(shippingNumber),
                        orderShipping.orderNumber.eq(orderNumber))
                .fetchFirst();
    }
}
