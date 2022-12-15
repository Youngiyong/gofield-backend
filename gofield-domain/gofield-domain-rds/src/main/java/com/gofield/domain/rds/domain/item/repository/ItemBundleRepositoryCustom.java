package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemBundle;
import com.gofield.domain.rds.domain.item.projection.ItemBundlePopularProjection;
import com.gofield.domain.rds.domain.item.projection.ItemBundleRecommendProjection;
import com.gofield.domain.rds.domain.item.projection.ItemBundleImageProjectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleRepositoryCustom {
    List<ItemBundlePopularProjection> findAllPopularBundleItemList(Pageable pageable);
    List<ItemBundlePopularProjection> findAllByCategoryId(Long categoryId, Long subCategoryId, Pageable pageable);
    List<ItemBundleRecommendProjection> findAllRecommendBundleItemList(Pageable pageable);
    ItemBundleImageProjectionResponse findByBundleId(Long userId, Long bundleId, Pageable pageable);
    ItemBundle findByBundleId(Long bundleId);
    Page<ItemBundle> findAllByKeyword(String keyword, Pageable pageable);
}
