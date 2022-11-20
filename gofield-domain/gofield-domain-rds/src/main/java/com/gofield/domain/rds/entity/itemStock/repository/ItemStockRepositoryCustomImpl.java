package com.gofield.domain.rds.entity.itemStock.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemStockRepositoryCustomImpl implements ItemStockRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
