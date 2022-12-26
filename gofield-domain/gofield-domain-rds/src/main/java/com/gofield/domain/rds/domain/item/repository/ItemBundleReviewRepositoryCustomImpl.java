package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemBundleReview;
import com.gofield.domain.rds.domain.item.projection.ItemBundleReviewScoreProjection;
import com.gofield.domain.rds.domain.item.projection.QItemBundleReviewScoreProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItemBundleReview.itemBundleReview;
import static com.gofield.domain.rds.domain.item.QItemBundleReviewImage.itemBundleReviewImage;


@RequiredArgsConstructor
public class ItemBundleReviewRepositoryCustomImpl implements ItemBundleReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<ItemBundleReview> findByBundleId(Long bundleId, Pageable pageable) {
        List<ItemBundleReview> result = jpaQueryFactory
                .select(itemBundleReview)
                .from(itemBundleReview)
                .leftJoin(itemBundleReview.images, itemBundleReviewImage).fetchJoin()
                .where(itemBundleReview.bundle.id.eq(bundleId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(itemBundleReview.id.desc())
                .fetch();

        if(result.isEmpty()){
            return new PageImpl<>(result, pageable, 0);
        }

        List<Long> totalCount = jpaQueryFactory
                .select(itemBundleReview.id)
                .from(itemBundleReview)
                .where(itemBundleReview.bundle.id.eq(bundleId))
                .fetch();

        return new PageImpl<>(result, pageable, totalCount.size());
    }

    @Override
    public List<ItemBundleReviewScoreProjection> findAllByBundleId(Long bundleId) {
        return jpaQueryFactory
                .select(new QItemBundleReviewScoreProjection(
                        itemBundleReview.id,
                        itemBundleReview.nickName,
                        itemBundleReview.reviewScore))
                .from(itemBundleReview)
                .where(itemBundleReview.bundle.id.eq(bundleId))
                .fetch();
    }

    @Override
    public List<ItemBundleReview> findAllByUserIdFetch(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(itemBundleReview)
                .from(itemBundleReview)
                .leftJoin(itemBundleReview.images, itemBundleReviewImage).fetchJoin()
                .where(itemBundleReview.userId.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(itemBundleReview.createDate.desc())
                .fetch();
    }
}
