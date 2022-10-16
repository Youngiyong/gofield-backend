package com.gofield.domain.rds.entity.userAccessLog;

import com.gofield.domain.rds.entity.userAccessLog.repository.UserAccessLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, Long>, UserAccessLogRepositoryCustom {
}
