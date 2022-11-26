package com.gofield.domain.rds.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderShippingRepositoryCustomImpl implements OrderShippingRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
