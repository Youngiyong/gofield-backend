package com.gofield.domain.rds.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCategoryRepositoryCustomImpl implements UserCategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
