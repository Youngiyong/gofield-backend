package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderSheet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.domain.order.QOrderSheet.orderSheet;

@RequiredArgsConstructor
public class OrderSheetRepositoryCustomImpl implements OrderSheetRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public OrderSheet findByUserIdAndUuid(Long userId, String uuid) {
        return jpaQueryFactory
                .select(orderSheet)
                .from(orderSheet)
                .where(orderSheet.userId.eq(userId),
                        orderSheet.uuid.eq(uuid))
                .fetchFirst();
    }
}
