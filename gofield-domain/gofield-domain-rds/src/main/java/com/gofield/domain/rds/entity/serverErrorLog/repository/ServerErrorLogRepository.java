package com.gofield.domain.rds.entity.serverErrorLog.repository;

import com.gofield.domain.rds.entity.serverErrorLog.ServerErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerErrorLogRepository extends JpaRepository<ServerErrorLog, Long>, ServerErrorLogRepositoryCustom {
}
