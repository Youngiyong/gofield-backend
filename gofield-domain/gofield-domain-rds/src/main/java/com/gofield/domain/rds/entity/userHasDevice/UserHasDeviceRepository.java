package com.gofield.domain.rds.entity.userHasDevice;

import com.gofield.domain.rds.entity.userHasDevice.repository.UserHasDeviceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserHasDeviceRepository extends JpaRepository<UserHasDevice, Long>, UserHasDeviceRepositoryCustom {
}
