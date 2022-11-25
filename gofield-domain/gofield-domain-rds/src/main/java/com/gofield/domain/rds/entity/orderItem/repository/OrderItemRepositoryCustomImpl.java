package com.gofield.domain.rds.entity.orderItem.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
