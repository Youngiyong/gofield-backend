package com.gofield.domain.rds.domain.cart.repository;


import com.gofield.domain.rds.domain.cart.CartTemp;

public interface CartTempRepositoryCustom {
    CartTemp findByUserIdAndUuid(Long userId, String uuid);
}
