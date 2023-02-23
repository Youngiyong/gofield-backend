package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderChangeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderChangeRepository extends JpaRepository<OrderChange, Long>, OrderChangeRepositoryCustom {
}
