package com.gofield.domain.rds.entity.item.repository;

import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemStatusFlag;
import com.gofield.domain.rds.projections.AdminInfoProjection;
import com.gofield.domain.rds.projections.ItemUsedRecentProjection;
import com.gofield.domain.rds.projections.QAdminInfoProjection;
import com.gofield.domain.rds.projections.QItemUsedRecentProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.entity.admin.QAdmin.admin;
import static com.gofield.domain.rds.entity.adminRole.QAdminRole.adminRole;
import static com.gofield.domain.rds.entity.item.QItem.item;
import static com.gofield.domain.rds.entity.itemStock.QItemStock.itemStock;
import static com.gofield.domain.rds.entity.itemBundle.QItemBundle.itemBundle;
import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemAggregation.QItemAggregation.itemAggregation;
import static com.gofield.domain.rds.entity.itemBundleAggregation.QItemBundleAggregation.itemBundleAggregation;


@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

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
