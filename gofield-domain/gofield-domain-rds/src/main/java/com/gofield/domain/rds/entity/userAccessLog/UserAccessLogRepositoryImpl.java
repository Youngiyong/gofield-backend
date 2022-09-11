package com.gofield.domain.rds.entity.userAccessLog;

import com.gofield.domain.rds.entity.userAccessLog.repository.UserAccessLogRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAccessLogRepositoryImpl implements UserAccessLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
