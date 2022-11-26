package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderShippingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingRepository extends JpaRepository<OrderShipping, Long>, OrderShippingRepositoryCustom {
}
