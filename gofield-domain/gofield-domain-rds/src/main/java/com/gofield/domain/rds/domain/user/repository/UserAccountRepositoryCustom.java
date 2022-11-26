package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserAccount;

public interface UserAccountRepositoryCustom {
    UserAccount findByUserId(Long userId);
}
