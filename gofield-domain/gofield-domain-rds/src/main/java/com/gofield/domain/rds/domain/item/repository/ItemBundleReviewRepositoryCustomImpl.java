package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemBundleReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItemBundleReview.itemBundleReview;
import static com.gofield.domain.rds.domain.item.QItemBundleReviewImage.itemBundleReviewImage;


@RequiredArgsConstructor
public class ItemBundleReviewRepositoryCustomImpl implements ItemBundleReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ItemBundleReview> findByBundleId(Long bundleId, Pageable pageable) {
        return jpaQueryFactory
                .select(itemBundleReview)
                .from(itemBundleReview)
                .leftJoin(itemBundleReview.images, itemBundleReviewImage).fetchJoin()
                .where(itemBundleReview.bundle.id.eq(bundleId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(itemBundleReview.createDate.desc())
                .fetch();
    }
}