package com.gofield.domain.rds.domain.item.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SellerRepositoryCustomImpl implements SellerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
