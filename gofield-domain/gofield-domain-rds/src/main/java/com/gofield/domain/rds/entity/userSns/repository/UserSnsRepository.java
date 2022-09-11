package com.gofield.domain.rds.entity.userSns.repository;

import com.gofield.domain.rds.entity.userSns.UserSns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSnsRepository extends JpaRepository<UserSns, Long>, UserSnsRepositoryCustom {
}
