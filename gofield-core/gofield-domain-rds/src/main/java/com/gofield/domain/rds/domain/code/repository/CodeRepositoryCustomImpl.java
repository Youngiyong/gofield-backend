package com.gofield.domain.rds.domain.code.repository;

import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.code.QCode.code1;


@RequiredArgsConstructor
public class CodeRepositoryCustomImpl implements CodeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression isHide(Boolean isHide){
        if (isHide == null || !isHide) {
            return code1.isHide.isFalse();
        }
        return null;
    }

    @Override
    public List<Code> findAllByGroupByIsHide(ECodeGroup group, Boolean isHide) {
        return jpaQueryFactory
                .selectFrom(code1)
                .where(code1.group.eq(group), code1.isActive.isTrue(), isHide(isHide))
                .orderBy(code1.sort.asc())
                .fetch();
    }

    @Override
    public Code findByCode(String code) {
        return jpaQueryFactory
                .selectFrom(code1)
                .where(code1.code.eq(code))
                .fetchFirst();
    }
}
