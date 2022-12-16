package com.gofield.domain.rds.domain.item.repository;

import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.item.ItemBundle;
import com.gofield.domain.rds.domain.item.projection.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


import static com.gofield.domain.rds.domain.item.QBrand.brand;
import static com.gofield.domain.rds.domain.item.QCategory.category;
import static com.gofield.domain.rds.domain.item.QItem.item;
import static com.gofield.domain.rds.domain.item.QItemBundle.itemBundle;
import static com.gofield.domain.rds.domain.item.QItemBundleAggregation.itemBundleAggregation;
import static com.gofield.domain.rds.domain.item.QItemBundleImage.itemBundleImage;
import static com.gofield.domain.rds.domain.item.QItemDetail.itemDetail;
import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;
import static com.gofield.domain.rds.domain.user.QUserLikeItem.userLikeItem;


@RequiredArgsConstructor
public class ItemBundleRepositoryCustomImpl implements ItemBundleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression containName(String keyword){
        if (keyword == null || keyword.equals("")) {
            return null;
        }
        return itemBundle.name.contains(keyword).or(itemBundle.category.name.contains(keyword).or(itemBundle.brand.name.contains(keyword)));
    }

    @Override
    public List<ItemBundlePopularProjection> findAllPopularBundleItemList(Pageable pageable) {
        return jpaQueryFactory
                .select(new QItemBundlePopularProjection(
                        itemBundle.id,
                        itemBundle.name,
                        itemBundle.brand.name.as("brandName"),
                        itemBundle.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                        itemBundleAggregation.reviewCount,
                        itemBundleAggregation.reviewScore,
                        itemBundleAggregation.newLowestPrice,
                        itemBundleAggregation.usedLowestPrice))
                .from(itemBundle)
                .innerJoin(itemBundleAggregation)
                .on(itemBundle.id.eq(itemBundleAggregation.bundle.id))
                .where(itemBundle.isActive.isTrue())
                .orderBy(itemBundleAggregation.reviewCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<ItemBundlePopularProjection> findAllByCategoryId(Long categoryId, Long subCategoryId, Pageable pageable) {
        if(subCategoryId!=null){
            return jpaQueryFactory
                    .select(new QItemBundlePopularProjection(
                            itemBundle.id,
                            itemBundle.name,
                            itemBundle.brand.name.as("brandName"),
                            itemBundle.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                            itemBundleAggregation.reviewCount,
                            itemBundleAggregation.reviewScore,
                            itemBundleAggregation.newLowestPrice,
                            itemBundleAggregation.usedLowestPrice))
                    .from(itemBundle)
                    .innerJoin(itemBundleAggregation)
                    .on(itemBundle.id.eq(itemBundleAggregation.bundle.id))
                    .where(itemBundle.category.id.eq(subCategoryId), itemBundle.isActive.isTrue())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(itemBundleAggregation.reviewCount.desc())
                    .fetch();

        } else {
            return jpaQueryFactory
                    .select(new QItemBundlePopularProjection(
                            itemBundle.id,
                            itemBundle.name,
                            itemBundle.brand.name.as("brandName"),
                            itemBundle.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                            itemBundleAggregation.reviewCount,
                            itemBundleAggregation.reviewScore,
                            itemBundleAggregation.newLowestPrice,
                            itemBundleAggregation.usedLowestPrice))
                    .from(itemBundle)
                    .innerJoin(itemBundleAggregation)
                    .on(itemBundle.id.eq(itemBundleAggregation.bundle.id))
                    .where(itemBundle.category.parent.id.eq(categoryId), itemBundle.isActive.isTrue())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(itemBundleAggregation.reviewCount.desc())
                    .fetch();
        }

    }

    @Override
    public List<ItemBundleRecommendProjection> findAllRecommendBundleItemList(Pageable pageable) {
        return jpaQueryFactory
                .select(new QItemBundleRecommendProjection(
                        itemBundle.id,
                        itemBundle.name,
                        itemBundle.brand.name.as("brandName"),
                        itemBundle.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                        itemBundleAggregation.reviewCount,
                        itemBundleAggregation.reviewScore,
                        itemBundleAggregation.newLowestPrice,
                        itemBundleAggregation.usedLowestPrice))
                .from(itemBundle)
                .innerJoin(itemBundleAggregation)
                .on(itemBundle.id.eq(itemBundleAggregation.bundle.id))
                .where(itemBundle.isRecommend.isTrue(), itemBundle.isActive.isTrue())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(itemBundleAggregation.reviewCount.desc())
                .fetch();
    }

    @Override
    public ItemBundleImageProjectionResponse findByBundleId(Long userId, Long bundleId, Pageable pageable) {
        ItemBundleImageProjection bundle = jpaQueryFactory
                .select(new QItemBundleImageProjection(
                        itemBundle.id,
                        itemBundle.name,
                        itemBundle.brand.name.as("brandName"),
                        itemBundle.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                        itemBundleAggregation.reviewCount,
                        itemBundleAggregation.reviewScore,
                        itemBundleAggregation.newLowestPrice,
                        itemBundleAggregation.usedLowestPrice))
                .from(itemBundleAggregation)
                .innerJoin(itemBundle)
                .on(itemBundleAggregation.bundle.id.eq(itemBundle.id))
                .where(itemBundle.id.eq(bundleId))
                .fetchOne();

        if(bundle==null) throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("존재하지 않는 <%s> bundleId 입니다.", bundleId) );

        List<String> bundleImages = jpaQueryFactory
                .select(itemBundleImage.image.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150))
                .from(itemBundleImage)
                .where(itemBundleImage.bundle.id.eq(bundleId))
                .fetch();

        if(userId!=null){
            List<ItemClassificationProjection> items = jpaQueryFactory
                    .select(new QItemClassificationProjection(
                            item.id,
                            item.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                            item.price,
                            userLikeItem.id.as("likeId"),
                            item.classification,
                            itemDetail.gender,
                            item.tags))
                    .from(item)
                    .innerJoin(itemStock)
                    .on(item.itemNumber.eq(itemStock.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .leftJoin(userLikeItem)
                    .on(userLikeItem.item.id.eq(item.id), userLikeItem.user.id.eq(userId))
                    .where(item.bundle.id.eq(bundle.getId()), itemStock.status.eq(EItemStatusFlag.SALE))
                    .orderBy(itemStock.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            return ItemBundleImageProjectionResponse.of(bundle, bundleImages, ItemClassificationProjectionResponse.of(items));

        } else {
            List<ItemNonMemberClassificationProjection> items = jpaQueryFactory
                    .select(new QItemNonMemberClassificationProjection(
                            item.id,
                            item.itemNumber,
                            item.name,
                            brand.name.as("brandName"),
                            item.thumbnail.prepend(Constants.CDN_URL).concat(Constants.RESIZE_150x150),
                            item.price,
                            item.classification,
                            itemDetail.gender,
                            item.tags))
                    .from(item)
                    .innerJoin(itemStock)
                    .on(item.itemNumber.eq(itemStock.itemNumber))
                    .innerJoin(category)
                    .on(item.category.id.eq(category.id))
                    .innerJoin(itemDetail)
                    .on(item.detail.id.eq(itemDetail.id))
                    .innerJoin(brand)
                    .on(item.brand.id.eq(brand.id))
                    .where(item.bundle.id.eq(bundle.getId()), itemStock.status.eq(EItemStatusFlag.SALE))
                    .orderBy(itemStock.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            return ItemBundleImageProjectionResponse.of(bundle, bundleImages, ItemClassificationProjectionResponse.ofNon(items));
        }
    }

    @Override
    public ItemBundle findByBundleIdFetchJoin(Long bundleId){
        return jpaQueryFactory
                .select(itemBundle)
                .from(itemBundle)
                .innerJoin(itemBundle.category, category).fetchJoin()
                .innerJoin(itemBundle.brand, brand).fetchJoin()
                .where(itemBundle.id.eq(bundleId))
                .fetchOne();
    }

    @Override
    public ItemBundle findByBundleId(Long bundleId) {
        return jpaQueryFactory
                .selectFrom(itemBundle)
                .where(itemBundle.id.eq(bundleId))
                .fetchFirst();
    }

    @Override
    public Page<ItemBundle> findAllByKeyword(String keyword, Pageable pageable) {
        List<ItemBundle> content =  jpaQueryFactory
                .selectFrom(itemBundle)
                .innerJoin(itemBundle.category, category).fetchJoin()
                .innerJoin(itemBundle.brand, brand).fetchJoin()
                .where(containName(keyword), itemBundle.isActive.isTrue())
                .orderBy(itemBundle.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if(content.isEmpty()){
            return new PageImpl<>(content, pageable, 0);
        }

        List<Long> totalCount = jpaQueryFactory
                .select(itemBundle.id)
                .from(itemBundle)
                .where(containName(keyword), itemBundle.isActive.isTrue())
                .fetch();

        return new PageImpl<>(content, pageable, totalCount.size());
    }
}
