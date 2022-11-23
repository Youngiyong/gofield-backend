package com.gofield.domain.rds.entity.itemBundleReview.repository;


import com.gofield.domain.rds.entity.itemBundleReview.ItemBundleReview;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleReviewRepositoryCustom {
    List<ItemBundleReview> findByBundleId(Long bundleId, Pageable pageable);
}

