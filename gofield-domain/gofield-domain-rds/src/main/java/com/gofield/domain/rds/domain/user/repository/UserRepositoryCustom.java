package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.common.EStatusFlag;

public interface UserRepositoryCustom {
    User findByIdAndStatusActive(Long userId);
    User findByUuidAndStatus(String uuid, EStatusFlag status);

    User findByUuid(String uuid);
    User findByUuidAndStatusActive(String uuid);
    Long findIdByUuidAndStatusActive(String uuid);

    int findUserActiveTotalCount();
    int findUserDeleteTotalCount();
    int findUserTotalCount();
}
