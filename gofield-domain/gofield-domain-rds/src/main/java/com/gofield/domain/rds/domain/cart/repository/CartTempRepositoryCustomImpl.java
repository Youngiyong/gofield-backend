package com.gofield.domain.rds.domain.cart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartTempRepositoryCustomImpl implements CartTempRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
