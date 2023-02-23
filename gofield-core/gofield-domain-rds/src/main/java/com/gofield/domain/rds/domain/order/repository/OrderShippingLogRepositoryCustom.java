package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderShippingLog;

public interface OrderShippingLogRepositoryCustom {
    OrderShippingLog findLastShippingStatus(Long shippingId, Long userId);
}
