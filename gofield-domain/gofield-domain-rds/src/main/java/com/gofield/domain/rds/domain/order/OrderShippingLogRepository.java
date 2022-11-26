package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderShippingLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingLogRepository extends JpaRepository<OrderShippingLog, Long>, OrderShippingLogRepositoryCustom {
}
