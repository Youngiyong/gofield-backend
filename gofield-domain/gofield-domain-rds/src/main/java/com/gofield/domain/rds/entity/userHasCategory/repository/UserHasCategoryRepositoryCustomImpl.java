package com.gofield.domain.rds.entity.userHasCategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserHasCategoryRepositoryCustomImpl implements UserHasCategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
