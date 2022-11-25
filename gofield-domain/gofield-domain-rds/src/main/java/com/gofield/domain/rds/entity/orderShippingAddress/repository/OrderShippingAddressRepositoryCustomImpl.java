package com.gofield.domain.rds.entity.orderShippingAddress.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderShippingAddressRepositoryCustomImpl implements OrderShippingAddressRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
