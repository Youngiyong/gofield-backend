package com.gofield.domain.rds.entity.category;

import com.gofield.domain.rds.entity.category.repository.CategoryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.entity.category.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Category> findAllIsActiveTrueOrderBySort() {
        return jpaQueryFactory
                .selectFrom(category)
                .where(category.isActive.isTrue().and(category.parent.isNull()))
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
