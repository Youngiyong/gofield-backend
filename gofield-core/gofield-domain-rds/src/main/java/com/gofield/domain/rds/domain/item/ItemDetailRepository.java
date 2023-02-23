package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long>, ItemDetailRepositoryCustom {
}
