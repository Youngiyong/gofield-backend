package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
}
