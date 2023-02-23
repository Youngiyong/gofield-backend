package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.domain.item.repository.BrandRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long>, BrandRepositoryCustom {
}
