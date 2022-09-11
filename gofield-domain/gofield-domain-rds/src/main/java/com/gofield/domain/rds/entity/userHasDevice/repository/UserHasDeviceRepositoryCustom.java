package com.gofield.domain.rds.entity.userHasDevice.repository;

import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;

public interface UserHasDeviceRepositoryCustom {

    UserHasDevice findByUserIdAndDeviceId(Long userId, Long deviceId);
    UserHasDevice findByUserIdAndDeviceKey(Long userId, String deviceKey);
}
