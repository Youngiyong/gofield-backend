package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.projection.ItemClassificationProjectionResponse;
import com.gofield.domain.rds.domain.item.projection.ItemListProjectionResponse;
import com.gofield.domain.rds.domain.item.projection.ItemOrderSheetProjection;
import com.gofield.domain.rds.domain.item.projection.ItemProjectionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Item findByItemId(Long itemId);
    Item findByItemNumber(String itemNumber);

    List<ItemClassificationProjectionResponse> findAllClassificationItemByCategoryIdAndUserId(Long userId, Long categoryId, EItemClassificationFlag classification, Pageable pageable);

    List<ItemClassificationProjectionResponse> findAllClassificationItemByBundleIdAndClassificationAndNotNqItemId(Long userId, Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable);

    List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable);
    ItemListProjectionResponse findAllClassificationItemByKeyword(String keyword, Long userId, Pageable pageable);
    ItemProjectionResponse findByItemNumberAndUserId(String itemNumber, Long userId);

    ItemOrderSheetProjection findItemOrderSheetByItemNumber(String itemNumber);
}

