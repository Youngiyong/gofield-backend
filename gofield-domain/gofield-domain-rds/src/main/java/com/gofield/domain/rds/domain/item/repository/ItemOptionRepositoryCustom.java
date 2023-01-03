package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.ItemOptionGroup;
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;

import java.util.List;

public interface ItemOptionRepositoryCustom {
    List<ItemOptionProjection> findAllItemOptionByItemId(Long itemId);
    ItemOption findByItemNumber(String itemNumber);
    ItemOption findByItemIdAndItemNumber(Long itemId, String itemNumber);
    List<ItemOption> findAllByItemIdAndInItemNumber(Long itemId, List<String> itemNumber);
    ItemOption findByOptionId(Long id);

    void deleteByItemIdAndInIdList(Long itemId, List<Long> idList);

}
