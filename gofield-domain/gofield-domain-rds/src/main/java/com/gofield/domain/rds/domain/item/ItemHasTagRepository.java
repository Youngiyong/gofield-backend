package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.ItemHasTagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemHasTagRepository extends JpaRepository<ItemHasTag, Long>, ItemHasTagRepositoryCustom {
}
