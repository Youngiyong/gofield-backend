package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemBundleAggregation;

public interface ItemBundleAggregationRepositoryCustom {
    ItemBundleAggregation findByBundleId(Long bundleId);

}

