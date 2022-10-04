package com.gofield.domain.rds.entity.userAccess.repository;

import com.gofield.domain.rds.entity.userAccess.UserAccess;

public interface UserAccessRepositoryCustom {

    UserAccess findByUserIdAndDeviceId(Long userId, Long deviceId);
    UserAccess findByDeviceIdAndAccessKey(Long deviceId, String accessKey);
    UserAccess findByAccessKey(String accessKey);
}
