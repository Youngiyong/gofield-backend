package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemStock;

import java.util.List;

public interface ItemStockRepositoryCustom {
    ItemStock findByItemNumber(String itemNumber);
}
