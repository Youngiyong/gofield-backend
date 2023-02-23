package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderCancelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelRepository extends JpaRepository<OrderCancel, Long>, OrderCancelRepositoryCustom {
}
