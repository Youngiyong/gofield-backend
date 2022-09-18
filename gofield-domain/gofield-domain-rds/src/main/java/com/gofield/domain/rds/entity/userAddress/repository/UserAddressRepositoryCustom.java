package com.gofield.domain.rds.entity.userAddress.repository;

import com.gofield.domain.rds.entity.userAddress.UserAddress;

import java.util.List;

public interface UserAddressRepositoryCustom {
    UserAddress findByIdAndUserId(Long id, Long userId);
}
