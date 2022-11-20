package com.gofield.domain.rds.entity.item.repository;


import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.projections.ItemUsedRecentProjection;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemUsedRecentProjection> findUsedItemRecentList(Long userId);
    Item findByItemId(Long itemId);
    Item findByItemNumber(String itemNumber);
}
