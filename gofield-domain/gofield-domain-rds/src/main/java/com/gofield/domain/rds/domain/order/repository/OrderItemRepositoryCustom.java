package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderItem;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemRepositoryCustom {
    OrderItem findByOrderItemId(Long id);
    List<OrderItemProjection> findAllByUserId(Long userId, Boolean isReview, Pageable pageable);
}
