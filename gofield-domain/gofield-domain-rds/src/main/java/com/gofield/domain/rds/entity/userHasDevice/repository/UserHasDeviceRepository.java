package com.gofield.domain.rds.entity.userHasDevice.repository;

import com.gofield.domain.rds.entity.userHasDevice.UserHasDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHasDeviceRepository extends JpaRepository<UserHasDevice, Long>, UserHasDeviceRepositoryCustom {
}
