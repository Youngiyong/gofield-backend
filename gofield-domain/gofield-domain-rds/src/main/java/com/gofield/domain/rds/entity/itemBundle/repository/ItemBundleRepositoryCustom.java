package com.gofield.domain.rds.entity.itemBundle.repository;


import com.gofield.domain.rds.entity.itemBundle.ItemBundle;
import com.gofield.domain.rds.projections.ItemBundleImageProjection;
import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
import com.gofield.domain.rds.projections.ItemBundleRecommendProjection;
import com.gofield.domain.rds.projections.response.ItemBundleImageProjectionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleRepositoryCustom {
    List<ItemBundlePopularProjection> findAllPopularBundleItemList();
    List<ItemBundlePopularProjection> findAllByCategoryId(Long categoryId, Long subCategoryId, Pageable pageable);
    List<ItemBundleRecommendProjection> findAllRecommendBundleItemList();
    ItemBundleImageProjectionResponse findByBundleId(Long userId, Long bundleId, Pageable pageable);
}
