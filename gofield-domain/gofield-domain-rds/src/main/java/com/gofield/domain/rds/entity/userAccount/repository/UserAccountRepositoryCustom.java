package com.gofield.domain.rds.entity.userAccount.repository;

import com.gofield.domain.rds.entity.userAccount.UserAccount;

public interface UserAccountRepositoryCustom {
    UserAccount findByUserId(Long userId);
}
