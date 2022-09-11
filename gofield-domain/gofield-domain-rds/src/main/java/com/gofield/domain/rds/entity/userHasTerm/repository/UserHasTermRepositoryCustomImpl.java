package com.gofield.domain.rds.entity.userHasTerm.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserHasTermRepositoryCustomImpl implements UserHasTermRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
