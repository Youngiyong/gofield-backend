package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderSheetRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long>, OrderSheetRepositoryCustom {
}
