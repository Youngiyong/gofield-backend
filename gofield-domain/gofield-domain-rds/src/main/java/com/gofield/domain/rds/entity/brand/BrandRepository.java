package com.gofield.domain.rds.entity.brand;

import com.gofield.domain.rds.entity.brand.repository.BrandRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long>, BrandRepositoryCustom {
}
