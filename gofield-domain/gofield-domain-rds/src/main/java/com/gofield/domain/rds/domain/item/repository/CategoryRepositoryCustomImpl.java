package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.Category;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Category> findAllIsActiveAndIsAttentionOrderBySort() {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.isActive.isTrue(), category.isAttention.isTrue())
                .orderBy(category.sort.asc())
                .fetch();
    }

    @Override
    public List<Category> findAllSubCategoryByCategoryId(Long categoryId) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.parent.id.eq(categoryId),
                        category.isActive.isTrue())
                .orderBy(category.sort.asc())
                .fetch();
    }

    @Override
    public List<Category> findAllByInId(List<Long> idList) {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.id.in(idList))
                .fetch();
    }
}
