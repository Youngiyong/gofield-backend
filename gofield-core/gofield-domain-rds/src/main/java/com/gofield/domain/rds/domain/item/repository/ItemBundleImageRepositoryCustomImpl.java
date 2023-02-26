package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemBundleImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.item.QItemBundleImage.itemBundleImage;


@RequiredArgsConstructor
public class ItemBundleImageRepositoryCustomImpl implements ItemBundleImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public ItemBundleImage findItemBundleImageById(Long id) {
        return jpaQueryFactory
                .selectFrom(itemBundleImage)
                .where(itemBundleImage.id.eq(id))
                .fetchFirst();
    }

    @Override
    public ItemBundleImage findByBundleIdOrderBySortDesc(Long bundleId) {
        return jpaQueryFactory
                .selectFrom(itemBundleImage)
                .where(itemBundleImage.bundle.id.eq(bundleId))
                .orderBy(itemBundleImage.sort.desc())
                .fetchFirst();
    }
}
