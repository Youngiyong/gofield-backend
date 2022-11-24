package com.gofield.domain.rds.entity.itemQna.repository;

import com.gofield.domain.rds.entity.itemQna.ItemQna;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemQnaRepositoryCustom {
    List<ItemQna> findAllByItemIdAndUserId(Long itemId, Long userId, Pageable pageable);
    ItemQna findByQnaIdAndItemId(Long qnaId, Long itemId);
    ItemQna findByQnaIdAndUserId(Long qnaId, Long userId);
}
