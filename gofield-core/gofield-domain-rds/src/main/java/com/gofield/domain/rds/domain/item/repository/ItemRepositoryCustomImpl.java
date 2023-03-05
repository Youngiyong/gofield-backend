package com.gofield.domain.rds.domain.item.repository;

import com.gofield.common.exception.ForbiddenException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.*;
import com.gofield.domain.rds.domain.item.ShippingTemplate;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.gofield.domain.rds.domain.cart.QCart.cart;
import static com.gofield.domain.rds.domain.item.QBrand.brand;
import static com.gofield.domain.rds.domain.item.QItemRecent.itemRecent;
import static com.gofield.domain.rds.domain.item.QShippingTemplate.shippingTemplate;

import static com.gofield.domain.rds.domain.item.QCategory.category;
import static com.gofield.domain.rds.domain.item.QItem.item;
import static com.gofield.domain.rds.domain.item.QItemDetail.itemDetail;
import static com.gofield.domain.rds.domain.item.QItemImage.itemImage;
import static com.gofield.domain.rds.domain.item.QItemOption.itemOption;
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

    private BooleanExpression eqStatus(EItemStatusFlag status){
        if(status == null){
            return null;
        }
        return itemStock.status.eq(status);
    }

    private BooleanExpression inCategoryId(List<Long> categoryIdList){
        if(categoryIdList == null){
            return null;
        } else if (categoryIdList.isEmpty()){
            return null;
        } else {
            Optional<Long> allCategoryId = categoryIdList.stream()
                    .filter(h -> h.equals(11L) || h.equals(10L))
                    .findFirst();

            if(!allCategoryId.isEmpty()){
                return null;
            }
        }

        return category.id.in(categoryIdList);
    }

    private BooleanExpression inSpec(List<EItemSpecFlag> spec){
        if(spec == null || spec.isEmpty()){
            return null;
        }
        return itemDetail.spec.in(spec);
    }

    private BooleanExpression containName(String keyword){
        if (keyword == null || keyword.equals("")) {
            return null;
        }
        return item.name.contains(keyword).or(item.category.name.contains(keyword).or(item.itemNumber.contains(keyword)));
    }

//    private List<OrderSpecifier> orderByItemClassificationSort(List<EItemSort> sorts){
//        List<OrderSpecifier> result = new ArrayList<>();
//        if(sorts==null || sorts.isEmpty()){
//            result.add(itemStock.createDate.desc());
//        } else {
//            sorts.forEach(sort -> {
//                if(sort.equals(EItemSort.NEWEST)){
//                    result.add(itemStock.createDate.desc());
//                } else if(sort.equals(EItemSort.OLDEST)){
//                    result.add(itemStock.createDate.asc());
//                } else if(sort.equals(EItemSort.LOWER_PRICE)){
//                    result.add(item.price.asc());
//                } else if(sort.equals(EItemSort.HIGHER_PRICE)){
//                    result.add(item.price.desc());
//                }
//            });
//        }
//        return result;
//    }
    private OrderSpecifier orderByItemSort(EItemSort sort){
        if(sort==null){
            return item.createDate.desc();
        }
        if(sort.equals(EItemSort.OLDEST)){
            return itemStock.createDate.asc();
        } else if(sort.equals(EItemSort.LOWER_PRICE)){
            return item.price.asc();
        } else if(sort.equals(EItemSort.HIGHER_PRICE)){
            return item.price.desc();
        }
        return item.createDate.desc();
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllClassificationItemByCategoryIdAndUserId(Long userId, EItemClassificationFlag classification, List<Long> categoryIdList, List<EItemSpecFlag> spec, EItemSort sort,  Pageable pageable) {
        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection =  jpaQueryFactory
                    .select(new QItemNonMemberClassificationProjection(
                            item.id,
                            itemStock.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail,
                            item.price,
                            item.deliveryPrice,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
                            item.isSoldOut,
                            item.createDate))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.itemNumber.eq(item.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(itemStock.status.ne(EItemStatusFlag.HIDE),
                            eqClassification(classification),
                            inCategoryId(categoryIdList),
                            inSpec(spec),
                            item.deleteDate.isNull())
                    .orderBy(orderByItemSort(sort))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            return ItemClassificationProjectionResponse.ofNon(projection);
        }

        List<ItemClassificationProjection> projection =  jpaQueryFactory
                .select(new QItemClassificationProjection(
                        item.id,
                        itemStock.itemNumber,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(itemStock.status.ne(EItemStatusFlag.HIDE),
                        eqClassification(classification),
                        inCategoryId(categoryIdList),
                        inSpec(spec),
                        item.deleteDate.isNull())
                .orderBy(orderByItemSort(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllClassificationItemByBundleIdAndClassificationAndNotNqItemId(Long userId, Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable) {
        Long categoryId = jpaQueryFactory
                .select(item.category.id)
                .from(item)
                .where(item.id.eq(itemId))
                .fetchFirst();

        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection =  jpaQueryFactory
                    .select(new QItemNonMemberClassificationProjection(
                            item.id,
                            item.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail,
                            item.price,
                            item.deliveryPrice,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
                            item.isSoldOut,
                            item.createDate))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.itemNumber.eq(item.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(item.id.ne(itemId),
                            item.category.id.eq(categoryId),
                            itemStock.status.ne(EItemStatusFlag.HIDE),
                            eqClassification(classification),
                            item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.id.ne(itemId),
                        item.category.id.eq(categoryId),
                        itemStock.status.ne(EItemStatusFlag.HIDE),
                        eqClassification(classification),
                        item.deleteDate.isNull())
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllByUserIdAndCategoryIdAndClassificationAndNeItemId(Long userId, Long categoryId, Long itemId, EItemClassificationFlag classification, Pageable pageable) {
        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection =  jpaQueryFactory
                    .select(new QItemNonMemberClassificationProjection(
                            item.id,
                            item.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail,
                            item.price,
                            item.deliveryPrice,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
                            item.isSoldOut,
                            item.createDate))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.itemNumber.eq(item.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(item.id.ne(itemId),
                            item.category.id.eq(categoryId),
                            item.isSoldOut.isFalse(),
                            itemStock.status.eq(EItemStatusFlag.SALE),
                            eqClassification(classification),
                            item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.id.ne(itemId),
                        item.category.id.eq(categoryId),
                        item.isSoldOut.isFalse(),
                        itemStock.status.eq(EItemStatusFlag.SALE),
                        eqClassification(classification),
                        item.deleteDate.isNull())
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllByUserIdAndBundleIdAndClassificationAndNeItemId(Long userId, Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable) {
        if(userId==null){
            List<ItemNonMemberClassificationProjection> projection =  jpaQueryFactory
                    .select(new QItemNonMemberClassificationProjection(
                            item.id,
                            item.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail,
                            item.price,
                            item.deliveryPrice,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
                            item.isSoldOut,
                            item.createDate))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.itemNumber.eq(item.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(item.id.ne(itemId),
                            item.bundle.id.eq(bundleId),
                            itemStock.status.eq(EItemStatusFlag.SALE),
                            item.isSoldOut.isFalse(),
                            eqClassification(classification),
                            item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.id.ne(itemId),
                        item.bundle.id.eq(bundleId),
                        itemStock.status.eq(EItemStatusFlag.SALE),
                        item.isSoldOut.isFalse(),
                        eqClassification(classification),
                        item.deleteDate.isNull())
                .orderBy(itemStock.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable) {
        if(userId==null) throw new ForbiddenException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "비회원은 불가합니다.");

        List<ItemClassificationProjection> projection = jpaQueryFactory
                .select(new QItemClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id))
                .where(userLikeItem.user.id.eq(userId),
                        item.deleteDate.isNull())
                .orderBy(userLikeItem.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return ItemClassificationProjectionResponse.of(projection);
    }

    @Override
    public List<ItemClassificationProjectionResponse> findAllRecentItemByUserId(Long userId) {
        List<ItemClassificationProjection> projection = jpaQueryFactory
                .select(new QItemClassificationProjection(
                        item.id,
                        item.itemNumber,
                        item.name,
                        brand.name.as("brandName"),
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(itemRecent)
                .on(itemStock.itemNumber.eq(itemRecent.itemNumber), itemRecent.userId.eq(userId))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(item.id.eq(userLikeItem.item.id), userLikeItem.user.id.eq(userId))
                .where(item.deleteDate.isNull())
                .orderBy(itemRecent.updateDate.desc())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.brand.id.eq(brandId),
                        itemStock.status.ne(EItemStatusFlag.HIDE),
                        item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.brand.id.eq(brandId),
                        itemStock.status.ne(EItemStatusFlag.HIDE), item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.name.contains(keyword),
                        itemStock.status.ne(EItemStatusFlag.HIDE),
                        item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.name.contains(keyword),
                        itemStock.status.ne(EItemStatusFlag.HIDE),
                        item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        userLikeItem.id.as("likeId"),
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .leftJoin(userLikeItem)
                .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)),
                        itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
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
                        item.thumbnail,
                        item.price,
                        item.deliveryPrice,
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        item.isSoldOut,
                        item.createDate))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)),
                        itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
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
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.name.contains(keyword),
                        itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
                .fetchOne();
    }

    private Long getTotalElementsByBrandId(Long brandId){
        return jpaQueryFactory
                .select(item.id.count())
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id),
                        itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
                .where(item.brand.id.eq(brandId))
                .fetchOne();
    }

    private Long getTotalElementsByCategoryId(Long categoryId){
        return jpaQueryFactory
                .select(item.id.count())
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .where(item.category.id.eq(categoryId).or(category.parent.id.eq(categoryId)),
                        itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
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
                            item.brand.id,
                            item.category.id,
                            item.shippingTemplate.id,
                            item.thumbnail,
                            itemDetail.description,
                            item.itemNumber,
                            item.price,
                            item.deliveryPrice,
                            itemStock.qty,
                            item.isOption,
                            item.isSoldOut,
                            itemStock.status,
                            item.classification,
                            itemDetail.spec,
                            item.delivery,
                            itemDetail.gender,
                            item.tags,
                            itemDetail.itemOption))
                    .from(itemStock)
                    .innerJoin(item)
                    .on(itemStock.itemNumber.eq(item.itemNumber), itemStock.itemNumber.eq(itemNumber))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .where(item.deleteDate.isNull())
                    .fetchOne();

            if(projection==null){
                throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST,  String.format("<%s> 존재하지 않는 상품번호입니다.", itemNumber));
            }

            ShippingTemplate resultShip = jpaQueryFactory
                    .select(shippingTemplate)
                    .from(shippingTemplate)
                    .where(shippingTemplate.id.eq(projection.getShippingTemplateId()))
                    .fetchOne();

            List<String> images = jpaQueryFactory
                    .select(itemImage.image)
                    .from(itemImage)
                    .where(itemImage.item.id.eq(projection.getId()))
                    .orderBy(itemImage.sort.asc())
                    .fetch();

            return ItemProjectionResponse.of(projection, resultShip, images);
        }

        ItemProjection projection = jpaQueryFactory
                .select(new QItemProjection(
                        item.id,
                        item.name,
                        brand.name.as("brandName"),
                        itemStock.sellerId,
                        item.bundle.id,
                        item.brand.id,
                        item.category.id,
                        item.shippingTemplate.id,
                        item.thumbnail,
                        itemDetail.description.as("description"),
                        item.itemNumber,
                        item.price,
                        item.deliveryPrice,
                        itemStock.qty,
                        itemStock.status,
                        userLikeItem.id.as("likeId"),
                        item.isOption,
                        item.isSoldOut,
                        item.classification,
                        itemDetail.spec,
                        item.delivery,
                        itemDetail.gender,
                        item.tags,
                        itemDetail.itemOption.as("option")))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber), itemStock.itemNumber.eq(itemNumber))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(itemDetail)
                .on(item.detail.id.eq(itemDetail.id))
                .leftJoin(userLikeItem)
                .on(item.id.eq(userLikeItem.item.id), userLikeItem.user.id.eq(userId))
                .where(item.deleteDate.isNull())
                .fetchOne();

        if(projection==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST,  String.format("<%s> 존재하지 않는 상품번호입니다.", itemNumber));
        }

        ShippingTemplate resultShip = jpaQueryFactory
                .select(shippingTemplate)
                .from(shippingTemplate)
                .where(shippingTemplate.id.eq(projection.getShippingTemplateId()))
                .fetchOne();

        List<String> images = jpaQueryFactory
                .select(itemImage.image)
                .from(itemImage)
                .where(itemImage.item.id.eq(projection.getId()))
                .orderBy(itemImage.sort.asc())
                .fetch();

        return ItemProjectionResponse.of(projection, resultShip, images);
    }

    @Override
    public ItemOrderSheetProjection findItemOrderSheetByItemNumber(Long userId, Boolean isCart, String itemNumber) {
        if(isCart){
            return jpaQueryFactory
                    .select(new QItemOrderSheetProjection(
                            item.id,
                            brand.name,
                            item.name,
                            itemOption.viewName,
                            itemStock.sellerId,
                            item.bundle.id,
                            item.category.id,
                            item.brand.id,
                            itemOption.id,
                            item.thumbnail,
                            itemStock.itemNumber,
                            cart.price,
                            itemOption.optionPrice,
                            item.delivery,
                            item.deliveryPrice,
                            itemOption.optionType,
                            itemStock.qty,
                            item.isOption,
                            itemStock.status,
                            shippingTemplate.isCondition,
                            shippingTemplate.condition,
                            shippingTemplate.chargeType,
                            shippingTemplate.charge,
                            shippingTemplate.feeJeju,
                            shippingTemplate.feeJejuBesides,
                            shippingTemplate.isPaid))
                    .from(itemStock)
                    .innerJoin(cart)
                    .on(itemStock.itemNumber.eq(cart.itemNumber), cart.itemNumber.eq(itemNumber), cart.userId.eq(userId))
                    .innerJoin(item)
                    .on(itemStock.item.id.eq(item.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .innerJoin(shippingTemplate)
                    .on(item.shippingTemplate.id.eq(shippingTemplate.id))
                    .leftJoin(itemOption)
                    .on(itemStock.itemNumber.eq(itemOption.itemNumber))
                    .where(item.deleteDate.isNull())
                    .fetchOne();
        }

        return jpaQueryFactory
                .select(new QItemOrderSheetProjection(
                        item.id,
                        brand.name,
                        item.name,
                        itemOption.viewName,
                        itemStock.sellerId,
                        item.bundle.id,
                        item.category.id,
                        item.brand.id,
                        itemOption.id,
                        item.thumbnail,
                        itemStock.itemNumber,
                        item.price,
                        itemOption.optionPrice,
                        item.delivery,
                        item.deliveryPrice,
                        itemOption.optionType,
                        itemStock.qty,
                        item.isOption,
                        itemStock.status,
                        shippingTemplate.isCondition,
                        shippingTemplate.condition,
                        shippingTemplate.chargeType,
                        shippingTemplate.charge,
                        shippingTemplate.feeJeju,
                        shippingTemplate.feeJejuBesides,
                        shippingTemplate.isPaid))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.item.id.eq(item.id), itemStock.itemNumber.eq(itemNumber))
                .innerJoin(brand)
                .on(item.brand.id.eq(brand.id))
                .innerJoin(shippingTemplate)
                .on(item.shippingTemplate.id.eq(shippingTemplate.id))
                .leftJoin(itemOption)
                .on(itemStock.itemNumber.eq(itemOption.itemNumber))
                .where(item.deleteDate.isNull())
                .fetchOne();
    }

    @Override
    public Item findLowestItemByBundleIdAndClassification(Long bundleId, EItemClassificationFlag classification) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .where(item.bundle.id.eq(bundleId), item.classification.eq(classification),
                        itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
                .orderBy(item.price.asc())
                .fetchFirst();
    }

    @Override
    public List<Item> findAllItemByBundleIdAndStatusSalesOrderByPrice(Long bundleId) {
        return jpaQueryFactory
                .selectFrom(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .where(item.bundle.id.eq(bundleId), itemStock.status.eq(EItemStatusFlag.SALE), item.deleteDate.isNull())
                .orderBy(item.price.asc())
                .fetch();
    }

    @Override
    public Item findLowestItemByBundleId(Long bundleId) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .where(item.bundle.id.eq(bundleId), item.deleteDate.isNull(),
                        itemStock.status.eq(EItemStatusFlag.SALE))
                .orderBy(item.price.asc())
                .fetchFirst();
    }

    @Override
    public Item findHighestItemByBundleId(Long bundleId) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .where(item.bundle.id.eq(bundleId), item.deleteDate.isNull(),
                        itemStock.status.eq(EItemStatusFlag.SALE))
                .orderBy(item.price.desc())
                .fetchFirst();
    }


    @Override
    public ItemBundleOptionProjection findItemBundleOption(String itemNumber) {
        return jpaQueryFactory
                .select(new QItemBundleOptionProjection(
                    item.id,
                    item.name,
                    itemOption.name,
                    itemStock.sellerId,
                    item.bundle.id,
                    itemOption.id,
                    item.thumbnail,
                    itemStock.itemNumber,
                    item.price,
                    itemOption.price,
                    itemOption.optionType,
                    itemStock.qty,
                    item.isOption,
                    itemStock.status))
                .from(itemStock)
                .innerJoin(item)
                .on(itemStock.itemNumber.eq(item.itemNumber))
                .leftJoin(itemOption)
                .on(itemStock.itemNumber.eq(itemOption.itemNumber))
                .where(itemStock.itemNumber.eq(itemNumber), item.deleteDate.isNull())
                .fetchOne();
    }

    @Override
    public Item findByIdNotFetch(Long itemId) {
        return jpaQueryFactory
                .select(item)
                .from(item)
                .where(item.id.eq(itemId))
                .fetchOne();
    }

    @Override
    public List<ItemInfoProjection> findAllByKeyword(String keyword, EItemStatusFlag status){
        return jpaQueryFactory
                .select(new QItemInfoProjection(
                        item.id,
                        item.name,
                        item.classification,
                        item.price,
                        item.category.name,
                        itemStock.status,
                        item.thumbnail,
                        item.isOption,
                        item.createDate))
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .where(containName(keyword), eqStatus(status), item.deleteDate.isNull())
                .fetch();
    }

    @Override
    public ItemInfoAdminProjectionResponse findAllByKeyword(String keyword, EItemStatusFlag status, Pageable pageable) {
        List<ItemInfoProjection> content =  jpaQueryFactory
                .select(new QItemInfoProjection(
                        item.id,
                        item.name,
                        item.classification,
                        item.price,
                        item.category.name,
                        itemStock.status,
                        item.thumbnail,
                        item.isOption,
                        item.createDate))
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .where(containName(keyword), eqStatus(status), item.deleteDate.isNull())
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(item.id)
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .where(containName(keyword), eqStatus(status))
                .fetch();

        List<EItemStatusFlag> allCount = jpaQueryFactory
                .select(itemStock.status)
                .from(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .innerJoin(category)
                .on(item.category.id.eq(category.id))
                .fetch();

        Long salesCount = allCount.stream().filter(p-> p.equals(EItemStatusFlag.SALE)).collect(Collectors.counting());
        Long hideCount = allCount.stream().filter(p -> p.equals(EItemStatusFlag.HIDE)).collect(Collectors.counting());
        Long soldOutCount = allCount.stream().filter(p-> p.equals(EItemStatusFlag.SOLD_OUT)).collect(Collectors.counting());

        if(content.isEmpty()){
            return ItemInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, 0), allCount.size(), salesCount, hideCount, soldOutCount);
        }
        return ItemInfoAdminProjectionResponse.of(new PageImpl<>(content, pageable, totalCount.size()), allCount.size(), salesCount, hideCount, soldOutCount);
    }


    @Override
    public void updateShippingTemplateId(Long shippingTemplateId, Long itemId) {
        jpaQueryFactory
                .update(item)
                .set(item.shippingTemplate.id, shippingTemplateId)
                .where(item.id.eq(itemId))
                .execute();
    }

    @Override
    public void deleteItemById(Long itemId) {
        jpaQueryFactory
                .delete(item)
                .where(item.id.eq(itemId))
                .execute();

//        jpaQueryFactory
//                .delete(itemImage)

    }

    @Override
    public Item findByItemId(Long itemId) {
        return jpaQueryFactory
                .selectFrom(item)
                .where(item.id.eq(itemId), item.deleteDate.isNull())
                .fetchOne();
    }


    @Override
    public Item findByItemNumber(String itemNumber) {
        return jpaQueryFactory
                .selectFrom(item)
                .innerJoin(itemStock)
                .on(item.itemNumber.eq(itemStock.itemNumber))
                .where(itemStock.itemNumber.eq(itemNumber), item.deleteDate.isNull())
                .fetchOne();
    }


}
