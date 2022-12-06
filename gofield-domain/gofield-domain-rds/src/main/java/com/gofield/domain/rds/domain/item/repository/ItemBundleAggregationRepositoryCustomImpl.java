package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemBundleAggregation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.item.QItemBundleAggregation.itemBundleAggregation;


@RequiredArgsConstructor
public class ItemBundleAggregationRepositoryCustomImpl implements ItemBundleAggregationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ItemBundleAggregation findByBundleId(Long bundleId) {
        return jpaQueryFactory
                .select(itemBundleAggregation)
                .from(itemBundleAggregation)
                .where(itemBundleAggregation.bundle.id.eq(bundleId))
                .fetchFirst();
    }
}
