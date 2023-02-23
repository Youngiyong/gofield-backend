package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserCategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long>, UserCategoryRepositoryCustom {
}
