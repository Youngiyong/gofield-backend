package com.gofield.domain.rds.domain.order.repository;


import com.gofield.domain.rds.domain.order.Order;

public interface OrderRepositoryCustom {
    Order findByOrderNumber(String orderNumber);
}
