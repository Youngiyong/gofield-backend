package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;

import java.util.List;

public interface ItemOptionRepositoryCustom {
    List<ItemOptionProjection> findAllItemOptionByItemId(Long itemId);
    ItemOption findByItemNumber(String itemNumber);
    ItemOption findByOptionId(Long id);
}
