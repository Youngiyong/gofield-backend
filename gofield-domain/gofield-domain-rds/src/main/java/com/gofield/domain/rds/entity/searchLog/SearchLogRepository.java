package com.gofield.domain.rds.entity.searchLog;

import com.gofield.domain.rds.entity.searchLog.repository.SearchLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long>, SearchLogRepositoryCustom {
}
