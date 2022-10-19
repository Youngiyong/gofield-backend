package com.gofield.domain.rds.entity.itemImage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemImageRepositoryCustomImpl implements ItemImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
