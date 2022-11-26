package com.gofield.domain.rds.entity.item.repository;


import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.projections.ItemClassificationProjection;
import com.gofield.domain.rds.projections.ItemUsedRecentProjection;
import com.gofield.domain.rds.projections.response.ItemClassificationProjectionResponse;
import com.gofield.domain.rds.projections.response.ItemProjectionResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Item findByItemId(Long itemId);
    Item findByItemNumber(String itemNumber);

    List<ItemClassificationProjectionResponse> findAllClassificationItemByCategoryIdAndUserId(Long userId, Long categoryId, EItemClassificationFlag classification, Pageable pageable);
    List<ItemClassificationProjectionResponse> findAllUserLikeItemList(Long userId, Pageable pageable);
    List<ItemClassificationProjectionResponse> findAllClassificationItemByKeyword(String keyword, Long userId, Pageable pageable);
    ItemProjectionResponse findByItemIdAndUserId(Long itemId, Long userId);
}
