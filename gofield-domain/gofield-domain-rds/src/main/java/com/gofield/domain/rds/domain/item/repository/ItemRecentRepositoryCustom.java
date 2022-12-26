package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemRecent;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRecentRepositoryCustom {

    ItemRecent findByItemNumberAndUserId(String itemNumber, Long userId);
}
