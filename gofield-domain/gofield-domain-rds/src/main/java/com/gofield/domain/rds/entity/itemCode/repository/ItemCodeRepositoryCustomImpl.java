package com.gofield.domain.rds.entity.itemCode.repository;

import com.gofield.domain.rds.entity.itemBundleImage.repository.ItemBundleImageRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ItemCodeRepositoryCustomImpl implements ItemBundleImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
