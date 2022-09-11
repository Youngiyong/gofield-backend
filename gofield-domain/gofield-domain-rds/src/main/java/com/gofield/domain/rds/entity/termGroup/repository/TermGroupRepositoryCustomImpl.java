package com.gofield.domain.rds.entity.termGroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TermGroupRepositoryCustomImpl implements TermGroupRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
