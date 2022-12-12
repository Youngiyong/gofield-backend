package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandRepositoryCustom {
    Page<Brand> findAllByKeyword(String keyword, Pageable pageable);
    Brand findByBrandId(Long id);
    Brand findByName(String name);
}
