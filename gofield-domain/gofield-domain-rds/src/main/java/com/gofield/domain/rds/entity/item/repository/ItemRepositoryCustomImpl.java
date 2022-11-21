package com.gofield.domain.rds.entity.item.repository;

import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemStatusFlag;
import com.gofield.domain.rds.projections.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.entity.category.QCategory.category;
import static com.gofield.domain.rds.entity.item.QItem.item;
import static com.gofield.domain.rds.entity.itemStock.QItemStock.itemStock;
import static com.gofield.domain.rds.entity.itemBundle.QItemBundle.itemBundle;
import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemAggregation.QItemAggregation.itemAggregation;
import static com.gofield.domain.rds.entity.itemBundleAggregation.QItemBundleAggregation.itemBundleAggregation;
import static com.gofield.domain.rds.entity.itemDetail.QItemDetail.itemDetail;


@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemRecentProjection> findAllRecentItemByCategoryIdAndUserId(Long userId, Long categoryId, Pageable pageable) {
        return jpaQueryFactory
                .select(new QItemRecentProjection(
                        item.id,
                        item.itemNumber,
                        brand.name.as("brandName"),
                        item.thumbnail,
                        item.price,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        item.gender,
                        itemDetail.option))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(category)
                .on(item.category.id.eq(category.id), category.parent.id.eq(categoryId))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(itemStock.status.eq(EItemStatusFlag.SALE))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ItemUsedRecentProjection> findUsedItemRecentList(Long userId) {
        return jpaQueryFactory
                .select(new QItemUsedRecentProjection(
                        item.id,
                        item.itemNumber,
                        brand.name.as("brandName"),
                        item.thumbnail,
                        item.price,
                        userLikeItem.id.as("likeId"),
                        item.classification))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(itemStock.status.eq(EItemStatusFlag.SALE), item.classification.eq(EItemClassificationFlag.USED))
                .orderBy(itemStock.createDate.desc())
                .limit(30)
                .fetch();
    }

    @Override
    public Item findByItemId(Long itemId) {
        return jpaQueryFactory
                .selectFrom(item)
                .where(item.id.eq(itemId))
                .fetchOne();
    }

    @Override
    public Item findByItemNumber(String itemNumber) {
        return jpaQueryFactory
                .selectFrom(item)
                .where(item.itemNumber.eq(itemNumber))
                .fetchOne();
    }


}
