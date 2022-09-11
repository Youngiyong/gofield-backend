package com.gofield.domain.rds.entity.userPush.repository;

import com.gofield.domain.rds.entity.userPush.UserPush;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPushRepository extends JpaRepository<UserPush, Long>, UserPushRepositoryCustom {
}
