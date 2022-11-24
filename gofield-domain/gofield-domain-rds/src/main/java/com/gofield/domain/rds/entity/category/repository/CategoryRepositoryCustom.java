package com.gofield.domain.rds.entity.category.repository;


import com.gofield.domain.rds.entity.category.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findAllIsActiveAndIsAttentionOrderBySort();
    List<Category> findAllSubCategoryByCategoryId(Long categoryId);
    List<Category> findAllByInId(List<Long> idList);

}
