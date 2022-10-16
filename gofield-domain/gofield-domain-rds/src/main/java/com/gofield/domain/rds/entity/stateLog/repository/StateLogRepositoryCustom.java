package com.gofield.domain.rds.entity.stateLog;

import com.gofield.domain.rds.entity.serverStatus.ServerStatus;
import com.gofield.domain.rds.entity.serverStatus.repository.ServerStatusRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateLogRepository extends JpaRepository<ServerStatus, Long>, ServerStatusRepositoryCustom {
}
