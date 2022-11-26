package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserSns;
import com.gofield.domain.rds.domain.user.ESocialFlag;

import java.util.List;

public interface UserSnsRepositoryCustom {
    List<Long> findIdListByUserId(Long userId);
    void withdraw(List<Long> userIdList);
    UserSns findByUniQueIdAndRoute(String uniqueId, ESocialFlag route);
}
