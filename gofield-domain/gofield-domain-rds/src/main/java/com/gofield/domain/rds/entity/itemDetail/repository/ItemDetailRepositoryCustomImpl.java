package com.gofield.domain.rds.entity.itemDetail.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemDetailRepositoryCustomImpl implements ItemDetailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
