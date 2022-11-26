package com.gofield.domain.rds.entity.popularKeyword;

import com.gofield.domain.rds.entity.popularKeyword.repository.PopularKeywordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularKeywordRepository extends JpaRepository<PopularKeyword, Long>, PopularKeywordRepositoryCustom {
}
