package com.gofield.domain.rds.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserTermRepositoryCustomImpl implements UserTermRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
