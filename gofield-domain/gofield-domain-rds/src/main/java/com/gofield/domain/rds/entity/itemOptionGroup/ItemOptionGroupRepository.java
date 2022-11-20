package com.gofield.domain.rds.entity.itemOptionGroup;

import com.gofield.domain.rds.entity.itemOptionGroup.repository.ItemOptionGroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionGroupRepository extends JpaRepository<ItemOptionGroup, Long>, ItemOptionGroupRepositoryCustom {
}
