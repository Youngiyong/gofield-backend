package com.gofield.domain.rds.entity.userWebAccessLog;

import com.gofield.domain.rds.entity.userWebAccessLog.repository.UserWebAccessLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWebWebAccessLogRepository extends JpaRepository<UserWebAccessLog, Long>, UserWebAccessLogRepositoryCustom {
}
