package com.gofield.domain.rds.entity.item.repository;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemStatusFlag;
import com.gofield.domain.rds.projections.*;
import com.gofield.domain.rds.projections.response.ItemProjectionResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.entity.category.QCategory.category;
import static com.gofield.domain.rds.entity.item.QItem.item;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemImage.QItemImage.itemImage;
import static com.gofield.domain.rds.entity.itemStock.QItemStock.itemStock;
import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemDetail.QItemDetail.itemDetail;


@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression eqClassification(EItemClassificationFlag classification) {
        if (classification == null) {
            return null;
        }
        return item.classification.eq(classification);
    }

    private BooleanExpression eqCategoryParentId(Long categoryId){
        if(categoryId == null){
            return null;
        }
        return category.parent.id.eq(categoryId);
    }

    @Override
    public List<ItemClassificationProjection> findAllClassificationItemByCategoryIdAndUserId(Long userId, Long categoryId, EItemClassificationFlag classification, Pageable pageable) {
        return jpaQueryFactory
                .select(new QItemClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
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
                .where(itemStock.status.eq(EItemStatusFlag.SALE),
                        eqClassification(classification),
                        eqCategoryParentId(categoryId))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ItemClassificationProjection> findAllUserLikeItemList(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(new QItemClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
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
                .innerJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id))
                .where(userLikeItem.user.id.eq(userId))
                .orderBy(userLikeItem.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public ItemProjectionResponse findByItemIdAndUserId(Long itemId, Long userId) {
        ItemProjection projection = jpaQueryFactory
                .select(new QItemProjection(
                        item.id,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail.prepend(Constants.CDN_URL),
                        item.itemNumber,
                        item.price,
                        itemStock.qty,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        itemDetail.option))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .leftJoin(userLikeItem)
                .on(item.id.eq(userLikeItem.item.id), userLikeItem.user.id.eq(userId))
                .where(item.id.eq(itemId))
                .fetchOne();

        if(projection==null){
            return null;
        }

        List<String> images = jpaQueryFactory
                .select(itemImage.image.prepend(Constants.CDN_URL))
                .from(itemImage)
                .where(itemImage.item.id.eq(itemId))
                .fetch();

        return ItemProjectionResponse.of(projection, images);
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
