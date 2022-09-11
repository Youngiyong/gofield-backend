package com.gofield.domain.rds.entity.serverErrorLog;

import com.gofield.domain.rds.entity.serverErrorLog.repository.ServerErrorLogRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerErrorLogRepositoryImpl implements ServerErrorLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
