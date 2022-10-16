package com.gofield.domain.rds.entity.stateLog;

import com.gofield.domain.rds.entity.stateLog.repository.StateLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateLogRepository extends JpaRepository<StateLog, Long>, StateLogRepositoryCustom {
}
