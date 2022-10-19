package com.gofield.domain.rds.entity.itemAggregation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemAggregationRepositoryCustomImpl implements ItemAggregationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
