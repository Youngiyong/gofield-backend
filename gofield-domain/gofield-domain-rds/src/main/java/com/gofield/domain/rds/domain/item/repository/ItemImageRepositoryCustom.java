package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemImage;

public interface ItemImageRepositoryCustom {
    ItemImage findByIdAndItemId(Long id, Long itemId);
}
