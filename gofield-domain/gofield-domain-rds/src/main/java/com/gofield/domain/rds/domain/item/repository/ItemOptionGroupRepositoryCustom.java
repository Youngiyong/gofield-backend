package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemOptionGroup;

import java.util.List;

public interface ItemOptionGroupRepositoryCustom {
    List<ItemOptionGroup> findAllItemOptionGroupByItemId(Long itemId);
}
