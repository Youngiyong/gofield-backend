package com.gofield.domain.rds.domain.search.repository;


import com.gofield.domain.rds.domain.search.SearchLog;

import java.util.List;

public interface SearchLogRepositoryCustom {
    List<SearchLog> findByUserIdLimit(Long userId, int limit);
}
