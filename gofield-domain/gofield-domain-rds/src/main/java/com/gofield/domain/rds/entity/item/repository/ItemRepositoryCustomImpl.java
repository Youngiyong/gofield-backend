package com.gofield.domain.rds.entity.item.repository;

import com.gofield.domain.rds.entity.itemBundle.repository.ItemBundleRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemBundleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
