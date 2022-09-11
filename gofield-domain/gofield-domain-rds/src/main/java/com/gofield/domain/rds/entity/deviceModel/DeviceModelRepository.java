package com.gofield.domain.rds.entity.deviceModel;

import com.gofield.domain.rds.entity.deviceModel.repository.DeviceModelRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long>, DeviceModelRepositoryCustom {
}
