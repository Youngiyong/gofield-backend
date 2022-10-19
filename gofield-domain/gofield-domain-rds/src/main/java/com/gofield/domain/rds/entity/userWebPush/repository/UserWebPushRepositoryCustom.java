package com.gofield.domain.rds.entity.userWebPush.repository;

import com.gofield.domain.rds.entity.userWebPush.UserWebPush;
import com.gofield.domain.rds.enums.EPlatformFlag;

public interface UserWebPushRepositoryCustom {
    UserWebPush findByUserIdAndPushKey(Long userId, String pushKey);
}
