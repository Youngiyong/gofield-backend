package com.gofield.domain.rds.entity.serverErrorLog;

import com.gofield.domain.rds.entity.serverErrorLog.repository.ServerErrorLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerErrorLogRepository extends JpaRepository<ServerErrorLog, Long>, ServerErrorLogRepositoryCustom {
}
