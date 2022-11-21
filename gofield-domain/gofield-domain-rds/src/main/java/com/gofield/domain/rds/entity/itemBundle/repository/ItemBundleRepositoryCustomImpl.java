package com.gofield.domain.rds.entity.itemBundle.repository;

import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
import com.gofield.domain.rds.projections.ItemBundleRecommendProjection;
import com.gofield.domain.rds.projections.QItemBundlePopularProjection;
import com.gofield.domain.rds.projections.QItemBundleRecommendProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.entity.itemBundle.QItemBundle.itemBundle;
import static com.gofield.domain.rds.entity.itemBundleAggregation.QItemBundleAggregation.itemBundleAggregation;


import static com.gofield.domain.rds.entity.item.QItem.item;
import static com.gofield.domain.rds.entity.itemBundle.QItemBundle.itemBundle;
import static com.gofield.domain.rds.entity.itemBundleAggregation.QItemBundleAggregation.itemBundleAggregation;
import static com.gofield.domain.rds.entity.itemStock.QItemStock.itemStock;
import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemDetail.QItemDetail.itemDetail;


@RequiredArgsConstructor
public class ItemBundleRepositoryCustomImpl implements ItemBundleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemBundlePopularProjection> findAllPopularBundleItemList() {
        return jpaQueryFactory
                .select(new QItemBundlePopularProjection(
                        itemBundle.id,
                        itemBundle.name,
                        itemBundle.brand.name.as("brandName"),
                        itemBundle.thumbnail,
                        itemBundleAggregation.reviewCount,
                        itemBundleAggregation.reviewScore,
                        itemBundleAggregation.newLowestPrice,
                        itemBundleAggregation.usedLowestPrice))
                .from(itemBundle)
                .innerJoin(itemBundleAggregation)
                .on(itemBundle.id.eq(itemBundleAggregation.bundle.id))
                .orderBy(itemBundleAggregation.reviewCount.desc())
                .limit(30)
                .fetch();
    }

    @Override
    public List<ItemBundleRecommendProjection> findAllRecommendBundleItemList() {
        return jpaQueryFactory
                .select(new QItemBundleRecommendProjection(
                        itemBundle.id,
                        itemBundle.name,
                        itemBundle.brand.name.as("brandName"),
                        itemBundle.thumbnail,
                        itemBundleAggregation.reviewCount,
                        itemBundleAggregation.reviewScore,
                        itemBundleAggregation.newLowestPrice,
                        itemBundleAggregation.usedLowestPrice))
                .from(itemBundle)
                .innerJoin(itemBundleAggregation)
                .on(itemBundle.id.eq(itemBundleAggregation.bundle.id))
                .orderBy(itemBundleAggregation.reviewCount.desc())
                .where(itemBundle.isRecommend.isTrue())
                .limit(30)
                .fetch();
    }
}
