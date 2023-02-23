package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ShippingTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.item.QShippingTemplate.shippingTemplate;


@RequiredArgsConstructor
public class ShippingTemplateRepositoryCustomImpl implements ShippingTemplateRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ShippingTemplate findByShippingTemplateId(Long id) {
        return jpaQueryFactory
                .selectFrom(shippingTemplate)
                .where(shippingTemplate.id.eq(id))
                .fetchFirst();
    }
}
