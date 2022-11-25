package com.gofield.domain.rds.entity.orderTemp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderTempRepositoryCustomImpl implements OrderTempRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
