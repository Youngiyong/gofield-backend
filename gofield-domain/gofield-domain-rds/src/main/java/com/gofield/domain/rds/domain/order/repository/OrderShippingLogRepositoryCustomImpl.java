package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderShippingLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.order.QOrderShippingLog.orderShippingLog;

@RequiredArgsConstructor
public class OrderShippingLogRepositoryCustomImpl implements OrderShippingLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public OrderShippingLog findLastShippingStatus(Long shippingId, Long userId) {
        return jpaQueryFactory
                .selectFrom(orderShippingLog)
                .where(orderShippingLog.orderShippingId.eq(shippingId),
                        orderShippingLog.userId.eq(userId),
                        orderShippingLog.status.notIn(
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN,
                                EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE))
                .orderBy(orderShippingLog.id.desc())
                .fetchFirst();
    }
}
