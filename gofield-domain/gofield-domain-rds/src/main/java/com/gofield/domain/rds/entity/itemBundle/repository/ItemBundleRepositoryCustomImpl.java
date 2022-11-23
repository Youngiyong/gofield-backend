package com.gofield.domain.rds.entity.itemBundle.repository;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.enums.item.EItemStatusFlag;
import com.gofield.domain.rds.projections.*;
import com.gofield.domain.rds.projections.response.ItemBundleImageProjectionResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.gofield.domain.rds.entity.category.QCategory.category;
import static com.gofield.domain.rds.entity.itemBundle.QItemBundle.itemBundle;
import static com.gofield.domain.rds.entity.itemBundleAggregation.QItemBundleAggregation.itemBundleAggregation;


import static com.gofield.domain.rds.entity.item.QItem.item;
import static com.gofield.domain.rds.entity.itemBundle.QItemBundle.itemBundle;
import static com.gofield.domain.rds.entity.itemBundleAggregation.QItemBundleAggregation.itemBundleAggregation;
import static com.gofield.domain.rds.entity.itemStock.QItemStock.itemStock;
import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemDetail.QItemDetail.itemDetail;
import static com.gofield.domain.rds.entity.itemBundleImage.QItemBundleImage.itemBundleImage;


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
                        itemBundle.thumbnail.prepend(Constants.CDN_URL),
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
                        itemBundle.thumbnail.prepend(Constants.CDN_URL),
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

    @Override
    public ItemBundleImageProjectionResponse findByBundleId(Long userId, Long bundleId, Pageable pageable) {
        ItemBundleImageProjection bundle = jpaQueryFactory
                .select(new QItemBundleImageProjection(
                        itemBundle.id,
                        itemBundle.name,
                        itemBundle.brand.name.as("brandName"),
                        itemBundle.thumbnail.prepend(Constants.CDN_URL),
                        itemBundleAggregation.reviewCount,
                        itemBundleAggregation.reviewScore,
                        itemBundleAggregation.newLowestPrice,
                        itemBundleAggregation.usedLowestPrice))
                .from(itemBundleAggregation)
                .innerJoin(itemBundle)
                .on(itemBundleAggregation.bundle.id.eq(itemBundle.id))
                .where(itemBundle.id.eq(bundleId))
                .fetchOne();

        if(bundle==null) return null;

        List<String> bundleImages = jpaQueryFactory
                .select(itemBundleImage.image.prepend(Constants.CDN_URL))
                .from(itemBundleImage)
                .where(itemBundleImage.bundle.id.eq(bundleId))
                .fetch();

        List<ItemClassificationProjection> items = jpaQueryFactory
                .select(new QItemClassificationProjection(
                        item.id,
                        item.itemNumber,
                        brand.name.as("brandName"),
                        item.thumbnail.prepend(Constants.CDN_URL),
                        item.price,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.gender,
                        itemDetail.option))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.bundle.id.eq(bundle.getId()))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemBundleImageProjectionResponse.of(bundle, bundleImages, items);
    }

}
