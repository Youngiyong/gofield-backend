package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.projection.ItemClassificationProjectionResponse;
import com.gofield.domain.rds.domain.item.projection.ItemProjectionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Item findByItemId(Long itemId);
    Item findByItemNumber(String itemNumber);

    List<ItemClassificationProjectionResponse> findAllClassificationItemByCategoryIdAndUserId(Long userId, Long categoryId, EItemClassificationFlag classification, Pageable pageable);
    List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable);
    List<ItemClassificationProjectionResponse> findAllClassificationItemByKeyword(String keyword, Long userId, Pageable pageable);
    ItemProjectionResponse findByItemNumberAndUserId(String itemNumber, Long userId);
}
