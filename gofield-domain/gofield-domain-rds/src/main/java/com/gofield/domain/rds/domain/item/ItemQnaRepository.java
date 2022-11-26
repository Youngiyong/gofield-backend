package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemQnaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemQnaRepository extends JpaRepository<ItemQna, Long>, ItemQnaRepositoryCustom {
}
