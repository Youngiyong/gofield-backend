package com.gofield.domain.rds.entity.userPush.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPushRepositoryCustomImpl implements UserPushRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
