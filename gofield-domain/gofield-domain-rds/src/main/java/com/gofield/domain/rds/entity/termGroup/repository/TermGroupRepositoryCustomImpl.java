package com.gofield.domain.rds.entity.termGroup.repository;

import com.gofield.domain.rds.entity.termGroup.TermGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.termGroup.QTermGroup.termGroup;
import static com.gofield.domain.rds.entity.term.QTerm.term;
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

