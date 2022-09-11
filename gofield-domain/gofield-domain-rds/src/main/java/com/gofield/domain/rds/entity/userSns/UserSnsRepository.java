package com.gofield.domain.rds.entity.userSns;

import com.gofield.domain.rds.entity.userSns.repository.UserSnsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSnsRepository extends JpaRepository<UserSns, Long>, UserSnsRepositoryCustom {
}
