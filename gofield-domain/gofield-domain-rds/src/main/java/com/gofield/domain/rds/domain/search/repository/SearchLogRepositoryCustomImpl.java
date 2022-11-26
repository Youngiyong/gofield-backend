package com.gofield.domain.rds.domain.search.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchLogRepositoryCustomImpl implements SearchLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
