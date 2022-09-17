package com.gofield.domain.rds.entity.user.repository;

import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.EStatusFlag;

public interface UserRepositoryCustom {
    User findByIdAndStatusActive(Long userId);
    User findByUuidAndStatus(String uuid, EStatusFlag status);

    User findByUuid(String uuid);
    User findByUuidAndStatusActive(String uuid);
    Long findIdByUuidAndStatusActive(String uuid);
}
