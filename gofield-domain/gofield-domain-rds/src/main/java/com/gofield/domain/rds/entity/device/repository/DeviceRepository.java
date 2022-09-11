package com.gofield.domain.rds.entity.device.repository;

import com.gofield.domain.rds.entity.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, DeviceRepositoryCustom {
}
