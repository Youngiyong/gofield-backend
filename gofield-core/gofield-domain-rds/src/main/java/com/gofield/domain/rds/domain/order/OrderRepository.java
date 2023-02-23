package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
