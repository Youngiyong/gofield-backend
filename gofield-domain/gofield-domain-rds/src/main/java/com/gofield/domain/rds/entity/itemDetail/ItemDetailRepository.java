package com.gofield.domain.rds.entity.itemDetail;

import com.gofield.domain.rds.entity.itemDetail.repository.ItemDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long>, ItemDetailRepositoryCustom {
}
