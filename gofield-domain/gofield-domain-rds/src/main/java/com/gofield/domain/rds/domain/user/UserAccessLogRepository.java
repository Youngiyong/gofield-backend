package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserAccessLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, Long>, UserAccessLogRepositoryCustom {
}
