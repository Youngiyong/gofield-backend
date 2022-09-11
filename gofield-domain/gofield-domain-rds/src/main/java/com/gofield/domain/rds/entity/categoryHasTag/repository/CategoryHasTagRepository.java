package com.gofield.domain.rds.entity.categoryHasTag.repository;

import com.gofield.domain.rds.entity.categoryHasTag.CategoryHasTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryHasTagRepository extends JpaRepository<CategoryHasTag, Long>, CategoryHasTagRepositoryCustom {
}
