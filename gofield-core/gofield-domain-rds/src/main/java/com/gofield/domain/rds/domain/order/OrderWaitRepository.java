package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderWaitRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderWaitRepository extends JpaRepository<OrderWait, Long>, OrderWaitRepositoryCustom {
}
