package com.gofield.domain.rds.entity.userKeyword;

import com.gofield.domain.rds.entity.userKeyword.repository.UserKeywordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long>, UserKeywordRepositoryCustom {
}
