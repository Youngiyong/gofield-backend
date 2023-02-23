package com.gofield.domain.rds.domain.order.repository;

import com.gofield.domain.rds.domain.order.OrderWait;

public interface OrderWaitRepositoryCustom {
    OrderWait findByOid(String findByOid);
}
