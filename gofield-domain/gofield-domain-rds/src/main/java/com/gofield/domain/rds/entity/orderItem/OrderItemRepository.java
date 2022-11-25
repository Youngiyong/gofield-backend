package com.gofield.domain.rds.entity.orderItem;

import com.gofield.domain.rds.entity.orderItem.repository.OrderItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
}
