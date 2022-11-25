package com.gofield.domain.rds.entity.cartOption.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartOptionRepositoryCustomImpl implements CartOptionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
