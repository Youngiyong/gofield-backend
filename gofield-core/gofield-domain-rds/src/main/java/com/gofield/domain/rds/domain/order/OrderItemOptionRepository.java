package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderItemOptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Long>, OrderItemOptionRepositoryCustom {
}
