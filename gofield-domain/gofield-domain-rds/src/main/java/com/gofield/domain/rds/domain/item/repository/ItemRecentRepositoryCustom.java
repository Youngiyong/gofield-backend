package com.gofield.domain.rds.domain.item.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRecentRepositoryCustom {

    List<Long> findAllItemIdList(Long userId, Pageable pageable);
}
