package com.gofield.domain.rds.entity.term.repository;

import com.gofield.domain.rds.entity.term.Term;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.entity.term.QTerm.term;
@RequiredArgsConstructor
public class TermRepositoryCustomImpl implements TermRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Term> findByGroupId(Long groupId) {
        return jpaQueryFactory
                .selectFrom(term)
                .where(term.groupId.eq(groupId))
                .fetch();
    }
    @Override
    public List<Term> findAllByInId(List<Long> idList) {
        return jpaQueryFactory
                .selectFrom(term)
                .where(term.id.in(idList))
                .fetch();
    }
}
