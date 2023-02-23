package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemOptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long>, ItemOptionRepositoryCustom {
}
