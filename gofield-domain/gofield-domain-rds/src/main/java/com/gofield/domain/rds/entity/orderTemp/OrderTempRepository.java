package com.gofield.domain.rds.entity.orderTemp;

import com.gofield.domain.rds.entity.orderTemp.repository.OrderTempRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTempRepository extends JpaRepository<OrderTemp, Long>, OrderTempRepositoryCustom {
}
