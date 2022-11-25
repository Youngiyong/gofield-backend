package com.gofield.domain.rds.entity.orderShippingLog;

import com.gofield.domain.rds.entity.orderShippingLog.repository.OrderShippingLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingLogRepository extends JpaRepository<OrderShippingLog, Long>, OrderShippingLogRepositoryCustom {
}
