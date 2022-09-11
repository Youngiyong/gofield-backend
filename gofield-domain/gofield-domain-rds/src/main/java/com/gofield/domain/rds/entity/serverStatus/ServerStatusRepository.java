package com.gofield.domain.rds.entity.serverStatus;

import com.gofield.domain.rds.entity.serverStatus.repository.ServerStatusRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerStatusRepository extends JpaRepository<ServerStatus, Long>, ServerStatusRepositoryCustom {
}
