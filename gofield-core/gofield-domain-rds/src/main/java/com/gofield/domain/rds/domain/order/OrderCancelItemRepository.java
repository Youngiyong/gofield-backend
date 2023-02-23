package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderCancelItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelItemRepository extends JpaRepository<OrderCancelItem, Long>, OrderCancelItemRepositoryCustom {
}
