package com.gofield.domain.rds.entity.deviceModel.repository;

import com.gofield.domain.rds.entity.deviceModel.DeviceModel;

public interface DeviceModelRepositoryCustom {
    DeviceModel findByModel(String model);
}
