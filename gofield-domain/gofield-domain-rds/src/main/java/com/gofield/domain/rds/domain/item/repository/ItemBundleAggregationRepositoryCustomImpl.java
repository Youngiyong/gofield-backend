package com.gofield.domain.rds.domain.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class ItemBundleAggregationRepositoryCustomImpl implements ItemBundleAggregationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
