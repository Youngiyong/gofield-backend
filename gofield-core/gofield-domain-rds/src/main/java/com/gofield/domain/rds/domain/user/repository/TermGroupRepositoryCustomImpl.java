package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.TermGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.user.QTerm.term;
import static com.gofield.domain.rds.domain.user.QTermGroup.termGroup;

@RequiredArgsConstructor
public class TermGroupRepositoryCustomImpl implements TermGroupRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public TermGroup findByGroupId(Long id) {
        return jpaQueryFactory
                .select(termGroup)
                .from(termGroup)
                .innerJoin(termGroup.terms, term).fetchJoin()
                .where(termGroup.id.eq(id))
                .fetchOne();

    }
}

