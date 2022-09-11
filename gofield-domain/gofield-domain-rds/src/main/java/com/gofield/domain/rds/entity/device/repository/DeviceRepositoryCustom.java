package com.gofield.domain.rds.entity.device.repository;

import com.gofield.domain.rds.entity.device.Device;

public interface DeviceRepositoryCustom {

    Device findByDeviceKey(String deviceKey);
}
