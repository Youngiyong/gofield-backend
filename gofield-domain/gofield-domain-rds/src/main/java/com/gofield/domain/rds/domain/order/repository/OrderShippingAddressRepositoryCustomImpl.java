package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.Order;
import com.gofield.domain.rds.domain.order.OrderShippingAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderShippingAddressRepositoryCustomImpl implements OrderShippingAddressRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
