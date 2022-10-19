package com.gofield.domain.rds.entity.category;

import com.gofield.domain.rds.entity.category.repository.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
}
