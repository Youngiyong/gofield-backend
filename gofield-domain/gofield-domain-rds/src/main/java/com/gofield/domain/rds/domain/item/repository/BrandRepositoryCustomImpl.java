package com.gofield.domain.rds.domain.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
public class BrandRepositoryCustomImpl implements BrandRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
