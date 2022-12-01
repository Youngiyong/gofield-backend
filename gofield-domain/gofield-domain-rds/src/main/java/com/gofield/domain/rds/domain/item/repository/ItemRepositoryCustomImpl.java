package com.gofield.domain.rds.domain.item.repository;

import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.Brand;
import com.gofield.domain.rds.domain.item.Category;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.item.projection.*;
import com.gofield.domain.rds.domain.seller.ShippingTemplate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.gofield.domain.rds.domain.item.QBrand.brand;
import static com.gofield.domain.rds.domain.seller.QShippingTemplate.shippingTemplate;
import static com.gofield.domain.rds.domain.item.QCategory.category;
import static com.gofield.domain.rds.domain.item.QItem.item;
import static com.gofield.domain.rds.domain.item.QItemDetail.itemDetail;
import static com.gofield.domain.rds.domain.item.QItemImage.itemImage;
import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;
import static com.gofield.domain.rds.domain.user.QUserLikeItem.userLikeItem;


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
                    .on(itemStock.item.itemNumber.eq(item.itemNumber))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
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
    public List<ItemClassificationProjectionResponse> findAllClassificationItemByBundleIdAndClassificationAndNotNqItemId(Long userId, Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable) {
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
                    .on(itemStock.item.itemNumber.eq(item.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(item.bundle.id.eq(bundleId), item.id.ne(itemId),
                            itemStock.status.eq(EItemStatusFlag.SALE),
                            eqClassification(classification))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.bundle.id.eq(bundleId), item.id.ne(itemId),
                        itemStock.status.eq(EItemStatusFlag.SALE),
                        eqClassification(classification))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable) {
        if(userId==null) return new ArrayList<>();

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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.brand.id.eq(brandId),
                        itemStock.status.eq(EItemStatusFlag.SALE))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.brand.id.eq(brandId),
                        itemStock.status.eq(EItemStatusFlag.SALE))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.name.contains(keyword),
                        itemStock.status.eq(EItemStatusFlag.SALE))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.name.contains(keyword),
                        itemStock.status.eq(EItemStatusFlag.SALE))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)),
                        itemStock.status.eq(EItemStatusFlag.SALE))
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
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)),
                        itemStock.status.eq(EItemStatusFlag.SALE))
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public ItemListProjectionResponse findAllClassificationItemByKeyword(String keyword, Long userId, Pageable pageable) {

        Category resultCat = jpaQueryFactory
                .selectFrom(category)
                .where(category.name.contains(keyword))
                .fetchFirst();

        if(resultCat!=null){
            if(userId!=null){
                List<ItemClassificationProjection> projection = findAllMemberClassificationByUserIdAndCategoryId(userId, resultCat.getId(), pageable);
                Long totalCount = getTotalElementsByCategoryId(resultCat.getId());
                return ItemListProjectionResponse.of(ItemClassificationProjectionResponse.of(projection), totalCount);
            } else {
                List<ItemNonMemberClassificationProjection> projection = findAllNonMemberClassificationByCategoryId(resultCat.getId(), pageable);
                Long totalCount = getTotalElementsByCategoryId(resultCat.getId());
                return ItemListProjectionResponse.of(ItemClassificationProjectionResponse.ofNon(projection), totalCount);
            }
        }

        Brand resultBra = jpaQueryFactory
                .selectFrom(brand)
                .where(brand.name.contains(keyword))
                .fetchFirst();

        if(resultBra!=null){
            if(userId!=null){
                List<ItemClassificationProjection> projection = findAllMemberClassificationByUserIdAndBrandId(userId, resultBra.getId(), pageable);
                Long totalCount = getTotalElementsByBrandId(resultBra.getId());
                return ItemListProjectionResponse.of(ItemClassificationProjectionResponse.of(projection), totalCount);
            } else {
                List<ItemNonMemberClassificationProjection> projection = findAllNonMemberClassificationByBrandId(resultBra.getId(), pageable);
                Long totalCount = getTotalElementsByBrandId(resultBra.getId());
                return ItemListProjectionResponse.of(ItemClassificationProjectionResponse.ofNon(projection), totalCount);
            }
        }

        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection = findAllNonMemberClassificationLikeKeyword(keyword, pageable);
            Long totalCount = getTotalElementsByContainKeyword(keyword);
            return ItemListProjectionResponse.of(ItemClassificationProjectionResponse.ofNon(projection), totalCount);
        }

        List<ItemClassificationProjection> projection = findAllMemberClassificationByUserIdAndLikeKeyword(userId, keyword, pageable);
        Long totalCount = getTotalElementsByContainKeyword(keyword);
        return ItemListProjectionResponse.of(ItemClassificationProjectionResponse.of(projection), totalCount);
    }

    private Long getTotalElementsByContainKeyword(String keyword){
        return jpaQueryFactory
                .select(item.id.count())
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.name.contains(keyword),
                        itemStock.status.eq(EItemStatusFlag.SALE))
                .fetchOne();
    }

    private Long getTotalElementsByBrandId(Long brandId){
        return jpaQueryFactory
                .select(item.id.count())
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id),
                        itemStock.status.eq(EItemStatusFlag.SALE))
                .where(item.brand.id.eq(brandId))
                .fetchOne();
    }

    private Long getTotalElementsByCategoryId(Long categoryId){
        return jpaQueryFactory
                .select(item.id.count())
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)),
                        itemStock.status.eq(EItemStatusFlag.SALE))
                .fetchOne();
    }

    @Override
    public ItemProjectionResponse findByItemNumberAndUserId(String itemNumber, Long userId){
        if(userId==null){
            ItemNonMemberProjection projection = jpaQueryFactory
                    .select(
                            new QItemNonMemberProjection(
                            item.id,
                            item.name,
                            brand.name.as("brandName"),
                            itemStock.sellerId,
                            item.bundle.id,
                            item.thumbnail.prepend(Constants.CDN_URL),
                            itemDetail.description,
                            item.itemNumber,
                            item.price,
                            itemStock.qty,
                            itemStock.status,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
                            itemDetail.option))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.item.itemNumber.eq(item.itemNumber), itemStock.itemNumber.eq(itemNumber))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .leftJoin(userLikeItem)
                    .on(item.id.eq(userLikeItem.item.id), userLikeItem.user.id.eq(userId))
                    .fetchOne();

            if(projection==null){
                throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST,  String.format("<%s> 존재하지 않는 상품번호입니다.", itemNumber));
            }

            ShippingTemplate resultShip = jpaQueryFactory
                    .select(shippingTemplate)
                    .from(shippingTemplate)
                    .where(shippingTemplate.seller.id.eq(projection.getSellerId()))
                    .fetchOne();

            List<String> images = jpaQueryFactory
                    .select(itemImage.image.prepend(Constants.CDN_URL))
                    .from(itemImage)
                    .where(itemImage.item.id.eq(projection.getId()))
                    .fetch();

            return ItemProjectionResponse.of(projection.getId(), projection.getName(), projection.getBrandName(),
                    projection.getThumbnail(), projection.getDescription(), projection.getItemNumber(), projection.getBundleId(), projection.getPrice(), projection.getQty(), projection.getStatus(),
                    null, projection.getClassification(), projection.getSpec(), projection.getDelivery(),
                    projection.getGender(), projection.getTags(), projection.getOption(), images, resultShip);
        }

        ItemProjection projection = jpaQueryFactory
                .select(new QItemProjection(
                        item.id,
                        item.name,
                        brand.name.as("brandName"),
                        itemStock.sellerId,
                        item.bundle.id,
                        item.thumbnail.prepend(Constants.CDN_URL),
                        itemDetail.description,
                        item.itemNumber,
                        item.price,
                        itemStock.qty,
                        itemStock.status,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        itemDetail.option))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.itemNumber.eq(item.itemNumber), itemStock.itemNumber.eq(itemNumber))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .leftJoin(userLikeItem)
                .on(item.id.eq(userLikeItem.item.id), userLikeItem.user.id.eq(userId))
                .fetchOne();

        if(projection==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST,  String.format("<%s> 존재하지 않는 상품번호입니다.", itemNumber));
        }

        ShippingTemplate resultShip = jpaQueryFactory
                .select(shippingTemplate)
                .from(shippingTemplate)
                .where(shippingTemplate.seller.id.eq(projection.getSellerId()))
                .fetchOne();

        List<String> images = jpaQueryFactory
                .select(itemImage.image.prepend(Constants.CDN_URL))
                .from(itemImage)
                .where(itemImage.item.id.eq(projection.getId()))
                .fetch();

        return ItemProjectionResponse.of(projection.getId(), projection.getName(), projection.getBrandName(),
                projection.getThumbnail(), projection.getDescription(), projection.getItemNumber(), projection.getBundleId(),  projection.getPrice(), projection.getQty(), projection.getStatus(),
                projection.getLikeId(), projection.getClassification(), projection.getSpec(), projection.getDelivery(),
                projection.getGender(), projection.getTags(), projection.getOption(), images, resultShip);
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
