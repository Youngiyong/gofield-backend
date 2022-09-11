package com.gofield.domain.rds.entity.userHasCategory.repository;

import com.gofield.domain.rds.entity.userHasCategory.UserHasCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHasCategoryRepository extends JpaRepository<UserHasCategory, Long>, UserHasCategoryRepositoryCustom {
}
