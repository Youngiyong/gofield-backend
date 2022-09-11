package com.gofield.domain.rds.entity.userSns.repository;

import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.enums.ESocialFlag;

public interface UserSnsRepositoryCustom {

    UserSns findByUniQueIdAndRoute(String uniqueId, ESocialFlag route);
}
