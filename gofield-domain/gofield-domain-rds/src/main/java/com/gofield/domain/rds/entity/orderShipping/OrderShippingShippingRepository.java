package com.gofield.domain.rds.entity.orderShipping;

import com.gofield.domain.rds.entity.orderShipping.repository.OrderShippingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingShippingRepository extends JpaRepository<OrderShipping, Long>, OrderShippingRepositoryCustom {
}
