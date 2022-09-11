package com.gofield.domain.rds.entity.deviceModel.repository;

import com.gofield.domain.rds.entity.deviceModel.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long>, DeviceModelRepositoryCustom {
}
