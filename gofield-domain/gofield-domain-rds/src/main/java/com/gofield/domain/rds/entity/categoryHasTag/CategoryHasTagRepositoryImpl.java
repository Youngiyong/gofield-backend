package com.gofield.domain.rds.entity.categoryHasTag;

import com.gofield.domain.rds.entity.categoryHasTag.repository.CategoryHasTagRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CategoryHasTagRepositoryImpl implements CategoryHasTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
