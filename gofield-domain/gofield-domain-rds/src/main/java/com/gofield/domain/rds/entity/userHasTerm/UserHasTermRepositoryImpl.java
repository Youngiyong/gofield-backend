package com.gofield.domain.rds.entity.userHasTerm;

import com.gofield.domain.rds.entity.userHasTerm.repository.UserHasTermRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserHasTermRepositoryImpl implements UserHasTermRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
