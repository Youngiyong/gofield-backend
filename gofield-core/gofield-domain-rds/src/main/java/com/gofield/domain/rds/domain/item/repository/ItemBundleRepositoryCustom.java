package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.EItemBundleSort;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.ItemBundle;
import com.gofield.domain.rds.domain.item.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemBundleRepositoryCustom {
    List<ItemBundlePopularProjection> findAllPopularBundleItemList(Pageable pageable);
    List<ItemBundlePopularProjection> findAllByCategoryId(Long categoryId, Long subCategoryId, EItemBundleSort sort, Pageable pageable);
    List<ItemBundleRecommendProjection> findAllRecommendBundleItemList(Pageable pageable);

    Page<ItemClassificationProjectionResponse> findAllItemByBundleIdAndClassification(Long userId, Long bundleId, EItemClassificationFlag classification, Pageable pageable);
    ItemBundleImageProjectionResponse findAggregationByBundleId(Long bundleId);
    ItemBundle findItemBundleImagesByBundleIdFetch(Long bundleId);
    ItemBundle findByBundleIdNotFetch(Long bundleId);
    ItemBundle findByBundleIdFetchJoin(Long bundleId);

    List<ItemBundle> findAllActive();
    Page<ItemBundle> findAllByKeywordAndCategoryId(String keyword, Long parentId, Long childId, Pageable pageable);
    List<ItemBundle> findAllByKeyword(String keyword);
    List<ItemBundle> findAllByName(String name);
}
