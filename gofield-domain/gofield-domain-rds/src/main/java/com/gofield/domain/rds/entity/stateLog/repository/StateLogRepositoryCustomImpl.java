package com.gofield.domain.rds.entity.stateLog.repository;

import com.gofield.domain.rds.entity.stateLog.StateLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.entity.stateLog.QStateLog.stateLog;

@RequiredArgsConstructor
public class StateLogRepositoryCustomImpl implements StateLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public StateLog findByState(String state) {
        return jpaQueryFactory
                .selectFrom(stateLog)
                .where(stateLog.state.eq(state))
                .fetchOne();
    }
}
