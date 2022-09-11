package com.gofield.domain.rds.entity.serverStatus;

import com.gofield.domain.rds.entity.serverStatus.repository.ServerStatusRepositoryCustom;
import com.gofield.domain.rds.enums.EServerType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.entity.serverStatus.QServerStatus.serverStatus;

@RequiredArgsConstructor
public class ServerStatusRepositoryImpl implements ServerStatusRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ServerStatus findByServerType(EServerType serverType) {
        return jpaQueryFactory
                .selectFrom(serverStatus)
                .where(serverStatus.server.eq(serverType))
                .fetchOne();
    }
}
