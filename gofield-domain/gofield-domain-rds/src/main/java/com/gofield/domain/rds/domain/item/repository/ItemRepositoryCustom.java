package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {

    void deleteItemById(Long itemId);
    Item findByItemId(Long itemId);
    Item findByItemNumber(String itemNumber);

    List<ItemClassificationProjectionResponse> findAllClassificationItemByCategoryIdAndUserId(Long userId, EItemClassificationFlag classification, List<Long> categoryId, List<EItemSpecFlag> spec, List<EItemSort> sort, Pageable pageable);

    List<ItemClassificationProjectionResponse> findAllClassificationItemByBundleIdAndClassificationAndNotNqItemId(Long userId, Long bundleId, Long itemId, EItemClassificationFlag classification, Pageable pageable);

    List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable);
    List<ItemClassificationProjectionResponse> findAllRecentItemByUserId(Long userId);
    ItemListProjectionResponse findAllClassificationItemByKeyword(String keyword, Long userId, Pageable pageable);
    ItemProjectionResponse findByItemNumberAndUserId(String itemNumber, Long userId);
    ItemOrderSheetProjection findItemOrderSheetByItemNumber(String itemNumber);
    Item findLowestItemByBundleIdAndClassification(Long bundleId, EItemClassificationFlag classification);
    List<Item> findAllItemByBundleIdAndStatusSalesOrderByPrice(Long bundleId);
    Item findLowestItemByBundleId(Long bundleId);
    Item findHighestItemByBundleId(Long bundleId);
    ItemBundleOptionProjection findItemBundleOption(String itemNumber);

    Item findByIdNotFetch(Long itemId);

    ItemInfoAdminProjectionResponse findAllByKeyword(String keyword, EItemStatusFlag status, Pageable pageable);
    List<ItemInfoProjection> findAllByKeyword(String keyword, EItemStatusFlag status);
}

