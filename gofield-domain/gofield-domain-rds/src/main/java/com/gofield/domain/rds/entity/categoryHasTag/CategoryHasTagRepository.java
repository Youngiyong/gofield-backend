package com.gofield.domain.rds.entity.categoryHasTag;

import com.gofield.domain.rds.entity.categoryHasTag.repository.CategoryHasTagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryHasTagRepository extends JpaRepository<CategoryHasTag, Long>, CategoryHasTagRepositoryCustom {
}
