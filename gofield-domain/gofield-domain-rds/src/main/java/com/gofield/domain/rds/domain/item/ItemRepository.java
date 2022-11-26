package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}
