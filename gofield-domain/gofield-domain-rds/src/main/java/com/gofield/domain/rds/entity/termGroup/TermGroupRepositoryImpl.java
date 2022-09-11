package com.gofield.domain.rds.entity.termGroup;

import com.gofield.domain.rds.entity.termGroup.repository.TermGroupRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TermGroupRepositoryImpl implements TermGroupRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
