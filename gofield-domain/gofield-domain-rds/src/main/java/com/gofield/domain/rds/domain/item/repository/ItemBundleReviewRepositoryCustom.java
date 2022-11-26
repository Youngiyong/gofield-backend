package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemBundleReview;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleReviewRepositoryCustom {
    List<ItemBundleReview> findByBundleId(Long bundleId, Pageable pageable);
}

