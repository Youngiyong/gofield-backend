package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.CategoryHasTagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryHasTagRepository extends JpaRepository<CategoryHasTag, Long>, CategoryHasTagRepositoryCustom {
}
