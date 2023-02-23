package com.gofield.domain.rds.domain.search;

import com.gofield.domain.rds.domain.search.repository.PopularKeywordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularKeywordRepository extends JpaRepository<PopularKeyword, Long>, PopularKeywordRepositoryCustom {
}
