package com.gofield.domain.rds.entity.tag;

import com.gofield.domain.rds.entity.tag.repository.TagRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
