package com.gofield.domain.rds.entity.itemHasTag;

import com.gofield.domain.rds.entity.itemHasTag.repository.ItemHasTagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemHasTagRepository extends JpaRepository<ItemHasTag, Long>, ItemHasTagRepositoryCustom {
}
