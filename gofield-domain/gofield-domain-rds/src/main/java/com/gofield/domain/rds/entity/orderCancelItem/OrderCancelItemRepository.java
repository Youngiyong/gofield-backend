package com.gofield.domain.rds.entity.orderCancelItem;

import com.gofield.domain.rds.entity.orderCancelItem.repository.OrderCancelItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelItemRepository extends JpaRepository<OrderCancelItem, Long>, OrderCancelItemRepositoryCustom {
}
