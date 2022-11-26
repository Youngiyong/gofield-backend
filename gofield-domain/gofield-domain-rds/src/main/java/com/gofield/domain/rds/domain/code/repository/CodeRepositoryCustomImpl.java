package com.gofield.domain.rds.domain.code.repository;

import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.code.QCode.code1;


@RequiredArgsConstructor
public class CodeRepositoryCustomImpl implements CodeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Code> findByGroup(ECodeGroup group) {
        return jpaQueryFactory
                .selectFrom(code1)
                .where(code1.group.eq(group))
                .fetch();
    }
}
