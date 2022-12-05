package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderShipping;

public interface OrderShippingRepositoryCustom {
    OrderShipping findByShippingNumberAndOrderNumber(String shippingNumber, String orderNumber);
}
