package com.gofield.domain.rds.entity.userPush;

import com.gofield.domain.rds.entity.userPush.repository.UserPushRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPushRepositoryImpl implements UserPushRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
