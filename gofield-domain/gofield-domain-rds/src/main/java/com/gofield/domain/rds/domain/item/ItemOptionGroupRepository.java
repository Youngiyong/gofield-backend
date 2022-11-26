package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemOptionGroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionGroupRepository extends JpaRepository<ItemOptionGroup, Long>, ItemOptionGroupRepositoryCustom {
}
