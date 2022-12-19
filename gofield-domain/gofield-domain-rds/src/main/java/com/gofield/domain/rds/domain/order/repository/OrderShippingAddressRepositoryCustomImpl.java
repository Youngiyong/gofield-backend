package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderShippingAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.order.QOrderShippingAddress.orderShippingAddress;

@RequiredArgsConstructor
public class OrderShippingAddressRepositoryCustomImpl implements OrderShippingAddressRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public OrderShippingAddress findByOrderNumber(String orderNumber) {
        return jpaQueryFactory
                .select(orderShippingAddress)
                .from(orderShippingAddress)
                .where(orderShippingAddress.orderNumber.eq(orderNumber))
                .fetchFirst();
    }
}
