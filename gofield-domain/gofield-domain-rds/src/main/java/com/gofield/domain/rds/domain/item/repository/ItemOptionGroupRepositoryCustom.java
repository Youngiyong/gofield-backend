package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemOptionGroup;
import com.gofield.domain.rds.domain.item.ItemOptionGroupRepository;

import java.util.List;

public interface ItemOptionGroupRepositoryCustom {
    List<ItemOptionGroup> findAllItemOptionGroupByItemId(Long itemId);
    ItemOptionGroup findByGroupIdAndItemId(Long id, Long itemId);

    List<ItemOptionGroup> findAllByItemIdAndInIdList(List<Long> idList, Long itemId);
}
