package com.gofield.domain.rds.entity.itemStock;

import com.gofield.domain.rds.entity.itemStock.repository.ItemStockRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemStockRepository extends JpaRepository<ItemStock, Long>, ItemStockRepositoryCustom {
}
