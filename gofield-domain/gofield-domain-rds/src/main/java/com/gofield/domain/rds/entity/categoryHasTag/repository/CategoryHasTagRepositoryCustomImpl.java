package com.gofield.domain.rds.entity.categoryHasTag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CategoryHasTagRepositoryCustomImpl implements CategoryHasTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
