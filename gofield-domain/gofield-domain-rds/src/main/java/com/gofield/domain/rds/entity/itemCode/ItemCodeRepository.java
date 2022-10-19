package com.gofield.domain.rds.entity.itemCode;

import com.gofield.domain.rds.entity.itemCode.repository.ItemCodeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCodeRepository extends JpaRepository<ItemCode, Long>, ItemCodeRepositoryCustom {
}
