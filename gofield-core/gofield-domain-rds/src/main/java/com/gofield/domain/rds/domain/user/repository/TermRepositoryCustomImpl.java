package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.Term;
import com.gofield.domain.rds.domain.user.ETermFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.user.QTerm.term;


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
