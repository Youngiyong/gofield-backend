package com.gofield.domain.rds.entity.userSns.repository;

import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.enums.ESocialFlag;

import java.util.List;

public interface UserSnsRepositoryCustom {
    List<Long> findIdListByUserId(Long userId);
    void withdraw(List<Long> userIdList);
    UserSns findByUniQueIdAndRoute(String uniqueId, ESocialFlag route);
}
