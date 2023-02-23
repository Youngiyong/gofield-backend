package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderWait;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.order.QOrderWait.orderWait;

@RequiredArgsConstructor
public class OrderWaitRepositoryCustomImpl implements OrderWaitRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public OrderWait findByOid(String oid) {
        return jpaQueryFactory
                .select(orderWait)
                .from(orderWait)
                .where(orderWait.oid.eq(oid))
                .fetchFirst();
    }
}
