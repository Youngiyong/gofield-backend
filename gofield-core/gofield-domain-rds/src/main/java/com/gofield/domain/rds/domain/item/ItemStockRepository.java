package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemStockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemStockRepository extends JpaRepository<ItemStock, Long>, ItemStockRepositoryCustom {
}
