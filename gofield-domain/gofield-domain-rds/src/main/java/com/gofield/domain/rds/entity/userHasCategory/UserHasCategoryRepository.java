package com.gofield.domain.rds.entity.userHasCategory;

import com.gofield.domain.rds.entity.userHasCategory.repository.UserHasCategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasCategoryRepository extends JpaRepository<UserHasCategory, Long>, UserHasCategoryRepositoryCustom {
}
