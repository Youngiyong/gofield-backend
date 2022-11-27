package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;

import java.util.List;

public interface ItemOptionRepositoryCustom {
    List<ItemOptionProjection> findAllItemOptionByItemId(Long itemId);
}
