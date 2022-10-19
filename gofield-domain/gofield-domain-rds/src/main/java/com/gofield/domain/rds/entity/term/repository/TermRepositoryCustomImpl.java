package com.gofield.domain.rds.entity.term.repository;

import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.enums.ETermFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.entity.term.QTerm.term;
import static com.gofield.domain.rds.entity.termGroup.QTermGroup.termGroup;
@RequiredArgsConstructor
public class TermRepositoryCustomImpl implements TermRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Term> findAllByInId(List<Long> idList) {
        return jpaQueryFactory
                .selectFrom(term)
                .where(term.id.in(idList))
                .fetch();
    }

    @Override
    public Term findByType(ETermFlag type) {
        return jpaQueryFactory
                .selectFrom(term)
                .where(term.type.eq(type))
                .fetchOne();
    }
}
