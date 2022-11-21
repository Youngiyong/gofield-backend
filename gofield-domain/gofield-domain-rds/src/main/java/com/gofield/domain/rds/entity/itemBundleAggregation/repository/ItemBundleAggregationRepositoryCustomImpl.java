package com.gofield.domain.rds.entity.itemBundleAggregation.repository;

import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
import com.gofield.domain.rds.projections.QItemBundlePopularProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;



@RequiredArgsConstructor
public class ItemBundleAggregationRepositoryCustomImpl implements ItemBundleAggregationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
