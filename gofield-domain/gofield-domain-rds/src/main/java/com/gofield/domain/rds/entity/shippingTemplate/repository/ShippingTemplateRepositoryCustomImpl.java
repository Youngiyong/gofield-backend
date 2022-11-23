package com.gofield.domain.rds.entity.shippingTemplate.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ShippingTemplateRepositoryCustomImpl implements ShippingTemplateRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
