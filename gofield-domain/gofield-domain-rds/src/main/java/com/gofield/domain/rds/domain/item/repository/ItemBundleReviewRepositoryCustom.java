package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemBundleReview;
import com.gofield.domain.rds.domain.item.projection.ItemBundleReviewScoreProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleReviewRepositoryCustom {
    Page<ItemBundleReview> findByBundleId(Long bundleId, Pageable pageable);
    List<ItemBundleReviewScoreProjection> findAllByBundleId(Long bundleId);
    List<ItemBundleReview> findAllByUserId(Long userId, Pageable pageable);
}

