package com.gofield.domain.rds.entity.itemOptionGroup.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemOptionGroupRepositoryCustomImpl implements ItemOptionGroupRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
