package com.gofield.domain.rds.entity.userAccessLog.repository;

import com.gofield.domain.rds.entity.userAccessLog.UserAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, Long>, UserAccessLogRepositoryCustom {
}
