package com.gofield.domain.rds.domain.search;

import com.gofield.domain.rds.domain.search.repository.SearchLogRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long>, SearchLogRepositoryCustom {
}
