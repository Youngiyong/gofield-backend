package com.gofield.domain.rds.domain.order.repository;


import com.gofield.domain.rds.domain.order.OrderSheet;

public interface OrderSheetRepositoryCustom {
    OrderSheet findByUserIdAndUuid(Long userId, String uuid);
}
