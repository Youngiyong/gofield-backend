package com.gofield.domain.rds.entity.orderItemOption;

import com.gofield.domain.rds.entity.orderItemOption.repository.OrderItemOptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Long>, OrderItemOptionRepositoryCustom {
}
