package com.gofield.domain.rds.entity.orderCancel;

import com.gofield.domain.rds.entity.orderCancel.repository.OrderCancelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelRepository extends JpaRepository<OrderCancel, Long>, OrderCancelRepositoryCustom {
}
