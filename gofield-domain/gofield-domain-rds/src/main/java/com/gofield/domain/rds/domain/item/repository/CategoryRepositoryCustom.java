package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> findAllIsActiveAndIsAttentionOrderBySort();
    List<Category> findAllSubCategoryByCategoryId(Long categoryId);
    List<Category> findAllByInId(List<Long> idList);
    Category findByName(String name);
    Category findByCategoryId(Long id);
    Category findByParentIdAndName(Long parentId, String name);
    List<Category> findAllIsActiveOrderBySort();
    Page<Category> findAllByKeyword(String keyword, Pageable pageable);

}
