package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemRecentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRecentRepository extends JpaRepository<ItemRecent, Long>, ItemRecentRepositoryCustom {
}
