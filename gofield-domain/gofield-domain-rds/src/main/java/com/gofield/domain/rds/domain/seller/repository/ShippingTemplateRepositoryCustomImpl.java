package com.gofield.domain.rds.domain.seller.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ShippingTemplateRepositoryCustomImpl implements ShippingTemplateRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
