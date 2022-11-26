package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderTempRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTempRepository extends JpaRepository<OrderTemp, Long>, OrderTempRepositoryCustom {
}
