package com.gofield.domain.rds.entity.userHasCategory;

import com.gofield.domain.rds.entity.userHasCategory.repository.UserHasCategoryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserHasCategoryRepositoryImpl implements UserHasCategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
