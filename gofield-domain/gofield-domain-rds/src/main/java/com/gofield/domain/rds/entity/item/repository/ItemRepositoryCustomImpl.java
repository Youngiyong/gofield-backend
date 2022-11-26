package com.gofield.domain.rds.entity.item.repository;

import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.brand.Brand;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.entity.category.QCategory;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemStatusFlag;
import com.gofield.domain.rds.projections.*;
import com.gofield.domain.rds.projections.response.ItemClassificationProjectionResponse;
import com.gofield.domain.rds.projections.response.ItemProjectionResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.gofield.domain.rds.entity.category.QCategory.category;
import static com.gofield.domain.rds.entity.itemHasTag.QItemHasTag.itemHasTag;
import static com.gofield.domain.rds.entity.tag.QTag.tag;
import static com.gofield.domain.rds.entity.item.QItem.item;
import static com.gofield.domain.rds.entity.brand.QBrand.brand;
import static com.gofield.domain.rds.entity.itemImage.QItemImage.itemImage;
import static com.gofield.domain.rds.entity.itemStock.QItemStock.itemStock;
import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;
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
    public List<ItemClassificationProjectionResponse> findAllClassificationItemByCategoryIdAndUserId(Long userId, Long categoryId, EItemClassificationFlag classification, Pageable pageable) {
        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection =  jpaQueryFactory
                    .select(new QItemNonMemberClassificationProjection(
                            item.id,
                            item.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail.prepend(Constants.CDN_URL),
                            item.price,
                            item.classification,
                            itemDetail.gender,
                            item.tags))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.item.id.eq(item.id))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(itemStock.status.eq(EItemStatusFlag.SALE),
                            eqClassification(classification),
                            eqCategoryParentId(categoryId))
                    .orderBy(itemStock.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            return ItemClassificationProjectionResponse.ofNon(projection);
        }

        List<ItemClassificationProjection> projection =  jpaQueryFactory
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
                        item.tags))
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

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable) {
        if(userId==null){
            return new ArrayList<>();
//            List<ItemNonMemberClassificationProjection> projection = jpaQueryFactory
//                    .select(new QItemNonMemberClassificationProjection(
//                            item.id,
//                            item.itemNumber,
//                            item.name,
//                            brand.name.as("brandName"),
//                            item.thumbnail.prepend(Constants.CDN_URL),
//                            item.price,
//                            item.classification,
//                            itemDetail.gender,
//                            item.tags))
//                    .from(itemStock)
//                    .innerJoin(item)
//                    .on(itemStock.item.id.eq(item.id))
//                    .innerJoin(category)
//                    .on(item.category.id.eq(category.id))
//                    .innerJoin(itemDetail)
//                    .on(item.detail.id.eq(itemDetail.id))
//                    .innerJoin(brand)
//                    .on(item.brand.id.eq(brand.id))
//                    .orderBy(itemStock.createDate.desc())
//                    .offset(pageable.getOffset())
//                    .limit(pageable.getPageSize())
//                    .fetch();

//            return ItemClassificationProjectionResponse.ofNon(projection);
        }

        List<ItemClassificationProjection> projection = jpaQueryFactory
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
                        item.tags))
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

        return ItemClassificationProjectionResponse.of(projection);
    }
    private List<ItemClassificationProjection> findAllMemberClassificationByUserIdAndBrandId(Long userId, Long brandId, Pageable pageable){
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
                        item.tags))
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
                .where(item.brand.id.eq(brandId))
                .orderBy(userLikeItem.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private List<ItemNonMemberClassificationProjection> findAllNonMemberClassificationByBrandId(Long brandId, Pageable pageable){
        return jpaQueryFactory
                .select(new QItemNonMemberClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail.prepend(Constants.CDN_URL),
                        item.price,
                        item.classification,
                        itemDetail.gender,
                        item.tags))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.brand.id.eq(brandId))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<ItemClassificationProjection> findAllMemberClassificationByUserIdAndLikeKeyword(Long userId, String keyword, Pageable pageable){
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
                        item.tags))
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
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.name.like(keyword))
                .orderBy(userLikeItem.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private List<ItemNonMemberClassificationProjection> findAllNonMemberClassificationLikeKeyword(String keyword, Pageable pageable){
        return jpaQueryFactory
                .select(new QItemNonMemberClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail.prepend(Constants.CDN_URL),
                        item.price,
                        item.classification,
                        itemDetail.gender,
                        item.tags))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.name.like(keyword))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }



    private List<ItemClassificationProjection> findAllMemberClassificationByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable){
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
                        item.tags))
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
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)))
                .orderBy(userLikeItem.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
    private List<ItemNonMemberClassificationProjection> findAllNonMemberClassificationByCategoryId(Long categoryId, Pageable pageable){
        return jpaQueryFactory
                .select(new QItemNonMemberClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail.prepend(Constants.CDN_URL),
                        item.price,
                        item.classification,
                        itemDetail.gender,
                        item.tags))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllClassificationItemByKeyword(String keyword, Long userId, Pageable pageable) {
        Category resultCat = jpaQueryFactory
                .selectFrom(category)
                .where(category.name.eq(keyword))
                .fetchFirst();

        if(resultCat!=null){
            if(userId!=null){
                List<ItemClassificationProjection> projection = findAllMemberClassificationByUserIdAndCategoryId(userId, resultCat.getId(), pageable);
                return ItemClassificationProjectionResponse.of(projection);
            } else {
                List<ItemNonMemberClassificationProjection> projection = findAllNonMemberClassificationByCategoryId(resultCat.getId(), pageable);
                return ItemClassificationProjectionResponse.ofNon(projection);

            }
        }

        Brand resultBra = jpaQueryFactory
                .selectFrom(brand)
                .where(brand.name.eq(keyword))
                .fetchFirst();

        if(resultBra!=null){
            if(userId!=null){
                List<ItemClassificationProjection> projection = findAllMemberClassificationByUserIdAndBrandId(userId, resultBra.getId(), pageable);
                return ItemClassificationProjectionResponse.of(projection);
            } else {
                List<ItemNonMemberClassificationProjection> projection = findAllNonMemberClassificationByBrandId(resultBra.getId(), pageable);
                return ItemClassificationProjectionResponse.ofNon(projection);
            }
        }

        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection = findAllNonMemberClassificationLikeKeyword(keyword, pageable);
            return ItemClassificationProjectionResponse.ofNon(projection);
        }

        List<ItemClassificationProjection> projection = findAllMemberClassificationByUserIdAndLikeKeyword(userId, keyword, pageable);
        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public ItemProjectionResponse findByItemIdAndUserId(Long itemId, Long userId){
        if(userId==null){
            ItemNonMemberProjection projection = jpaQueryFactory
                    .select(new QItemNonMemberProjection(
                            item.id,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail.prepend(Constants.CDN_URL),
                            item.itemNumber,
                            item.price,
                            itemStock.qty,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
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
                throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 상품 아이디입니다.");
            }

            List<String> images = jpaQueryFactory
                    .select(itemImage.image.prepend(Constants.CDN_URL))
                    .from(itemImage)
                    .where(itemImage.item.id.eq(itemId))
                    .fetch();

            return ItemProjectionResponse.of(projection.getId(), projection.getName(), projection.getBrandName(),
                    projection.getThumbnail(), projection.getItemNumber(), projection.getPrice(), projection.getQty(),
                    null, projection.getClassification(), projection.getSpec(), projection.getDelivery(),
                    projection.getGender(), projection.getTags(), projection.getOption(), images);
        }

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
                        item.tags,
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

        return ItemProjectionResponse.of(projection.getId(), projection.getName(), projection.getBrandName(),
                projection.getThumbnail(), projection.getItemNumber(), projection.getPrice(), projection.getQty(),
                projection.getLikeId(), projection.getClassification(), projection.getSpec(), projection.getDelivery(),
                projection.getGender(), projection.getTags(), projection.getOption(), images);

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
