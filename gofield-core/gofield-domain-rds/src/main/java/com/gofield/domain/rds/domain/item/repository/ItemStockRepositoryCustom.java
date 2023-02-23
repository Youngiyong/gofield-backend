package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemStock;

import java.util.List;

public interface ItemStockRepositoryCustom {
    ItemStock findByItemNumber(String itemNumber);
    ItemStock findItemByItemNumber(String itemNumber);
    List<ItemStock> findAllInItemNumber(List<String> itemNumberList);
    ItemStock findShippingTemplateByItemNumberFetch(String itemNumber);
    void deleteIdList(List<Long> idList, Long itemId);
}
