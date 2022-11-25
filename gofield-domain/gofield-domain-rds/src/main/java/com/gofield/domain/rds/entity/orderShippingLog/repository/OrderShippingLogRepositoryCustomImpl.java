package com.gofield.domain.rds.entity.orderShippingLog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderShippingLogRepositoryCustomImpl implements OrderShippingLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
