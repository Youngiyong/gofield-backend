package com.gofield.domain.rds.entity.itemQna;

import com.gofield.domain.rds.entity.itemCode.ItemCode;
import com.gofield.domain.rds.entity.itemQna.repository.ItemQnaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemQnaRepository extends JpaRepository<ItemQna, Long>, ItemQnaRepositoryCustom {
}
