package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserSnsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSnsRepository extends JpaRepository<UserSns, Long>, UserSnsRepositoryCustom {
}
