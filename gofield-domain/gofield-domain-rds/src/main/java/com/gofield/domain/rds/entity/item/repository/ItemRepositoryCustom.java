package com.gofield.domain.rds.entity.item.repository;


import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.projections.ItemRecentProjection;
import com.gofield.domain.rds.projections.ItemUsedRecentProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemUsedRecentProjection> findUsedItemRecentList(Long userId);
    Item findByItemId(Long itemId);
    Item findByItemNumber(String itemNumber);

    List<ItemRecentProjection> findAllRecentItemByCategoryIdAndUserId(Long userId, Long categoryId, Pageable pageable);
}
