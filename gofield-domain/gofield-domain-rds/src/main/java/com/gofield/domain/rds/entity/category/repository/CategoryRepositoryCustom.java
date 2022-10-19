package com.gofield.domain.rds.entity.category.repository;


import com.gofield.domain.rds.entity.category.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findAllIsActiveOrderBySort();
    List<Category> findAllByInId(List<Long> idList);

}
