package com.gofield.domain.rds.domain.order.repository;


import com.gofield.domain.rds.domain.order.OrderShippingAddress;

public interface OrderShippingAddressRepositoryCustom {
    OrderShippingAddress findByOrderNumber(String orderNumber);

}
