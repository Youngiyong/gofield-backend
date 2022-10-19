package com.gofield.domain.rds.entity.itemBundleImage.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemBundleImageRepositoryCustomImpl implements ItemBundleImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
