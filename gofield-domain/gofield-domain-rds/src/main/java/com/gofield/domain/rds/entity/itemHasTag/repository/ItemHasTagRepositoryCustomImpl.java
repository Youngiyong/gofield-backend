package com.gofield.domain.rds.entity.itemHasTag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;



@RequiredArgsConstructor
public class ItemHasTagRepositoryCustomImpl implements ItemHasTagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
