package com.gofield.domain.rds.entity.itemQna.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemQnaRepositoryCustomImpl implements ItemQnaRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
