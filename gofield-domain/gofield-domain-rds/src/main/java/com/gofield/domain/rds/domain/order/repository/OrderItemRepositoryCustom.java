package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderItem;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemRepositoryCustom {
    OrderItem findByOrderItemId(Long id);
    OrderItem findByOrderItemIdAndUserId(Long id, Long userId);

    OrderItem findByOrderItemIdFetch(Long id, Long userId);

    List<OrderItemProjection> findAllByUserIdAndShippingStatusShippingComplete(Long userId, Pageable pageable);
}
