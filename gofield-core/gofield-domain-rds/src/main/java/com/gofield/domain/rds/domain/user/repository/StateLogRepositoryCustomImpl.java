package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.StateLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.domain.user.QStateLog.stateLog;

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
