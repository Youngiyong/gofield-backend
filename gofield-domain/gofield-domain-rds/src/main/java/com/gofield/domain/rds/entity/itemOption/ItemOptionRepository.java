package com.gofield.domain.rds.entity.itemOption;

import com.gofield.domain.rds.entity.itemOption.repository.ItemOptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long>, ItemOptionRepositoryCustom {
}
