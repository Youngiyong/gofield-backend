package com.gofield.domain.rds.entity.topKeyword;

import com.gofield.domain.rds.entity.topKeyword.repository.TopKeywordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopKeywordRepository extends JpaRepository<TopKeyword, Long>, TopKeywordRepositoryCustom {
}
