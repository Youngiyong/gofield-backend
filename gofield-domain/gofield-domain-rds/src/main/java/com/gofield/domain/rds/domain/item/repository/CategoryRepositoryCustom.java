package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findAllIsActiveAndIsAttentionOrderBySort();
    List<Category> findAllSubCategoryByCategoryId(Long categoryId);
    List<Category> findAllByInId(List<Long> idList);

}
