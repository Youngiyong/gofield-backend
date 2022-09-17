package com.gofield.domain.rds.entity.userPush.repository;

import com.gofield.domain.rds.entity.userPush.UserPush;
import com.gofield.domain.rds.enums.EPlatformFlag;

public interface UserPushRepositoryCustom {
    UserPush findByPlatformAndPushKey(EPlatformFlag platform, String pushKey);
}
