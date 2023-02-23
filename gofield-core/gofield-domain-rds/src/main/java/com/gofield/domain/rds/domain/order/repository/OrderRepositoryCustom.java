package com.gofield.domain.rds.domain.order.repository;


import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {
    Order findByOrderNumberAndUserIdAndNotStatusDelete(Long userId, String orderNumber);
    Order findByOrderNumber(String orderNumber);
    List<Order> findAllByUserIdAndNotStatusDelete(Long userId, EOrderShippingStatusFlag status, Pageable pageable);
    Order findByOrderNumberAndUserId(String orderNumber, Long userId);
}
