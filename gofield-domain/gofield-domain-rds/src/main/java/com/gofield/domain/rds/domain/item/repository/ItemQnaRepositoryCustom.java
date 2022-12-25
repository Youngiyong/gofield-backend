package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemQnaRepositoryCustom {
    Page<ItemQna> findAllByItemIdAndUserId(Long itemId, Long userId, Pageable pageable);
    ItemQna findByQnaIdAndItemId(Long qnaId, Long itemId);
    ItemQna findByQnaIdAndUserId(Long qnaId, Long userId);
}
