package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemBundleImage;

public interface ItemBundleImageRepositoryCustom {
    ItemBundleImage findItemBundleImageById(Long id);
    ItemBundleImage findByBundleIdOrderBySortDesc(Long bundleId);
}
