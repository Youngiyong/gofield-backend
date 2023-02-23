package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserAddress;

public interface UserAddressRepositoryCustom {
    UserAddress findByIdAndUserId(Long id, Long userId);
    UserAddress findByUserIdOrderByMain(Long userId);
}
