package com.gofield.domain.rds.entity.serverStatus.repository;

import com.gofield.domain.rds.entity.serverStatus.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerStatusRepository extends JpaRepository<ServerStatus, Long>, ServerStatusRepositoryCustom {
}
