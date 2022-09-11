package com.gofield.domain.rds.entity.device;

import com.gofield.domain.rds.entity.device.repository.DeviceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long>, DeviceRepositoryCustom {
}
