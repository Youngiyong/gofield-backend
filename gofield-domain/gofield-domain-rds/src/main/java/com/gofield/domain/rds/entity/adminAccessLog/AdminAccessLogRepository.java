package com.gofield.domain.rds.entity.adminAccessLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminAccessLogRepository extends JpaRepository<AdminAccessLog, Long> {
}
