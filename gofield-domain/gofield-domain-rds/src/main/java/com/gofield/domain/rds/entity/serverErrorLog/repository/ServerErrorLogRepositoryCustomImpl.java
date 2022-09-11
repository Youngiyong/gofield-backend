package com.gofield.domain.rds.entity.serverErrorLog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerErrorLogRepositoryCustomImpl implements ServerErrorLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
