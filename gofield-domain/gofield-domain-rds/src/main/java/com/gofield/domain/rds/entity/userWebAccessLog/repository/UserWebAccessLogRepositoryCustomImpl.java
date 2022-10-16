package com.gofield.domain.rds.entity.userWebAccessLog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserWebAccessLogRepositoryCustomImpl implements UserWebAccessLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
