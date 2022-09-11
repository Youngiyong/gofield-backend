package com.gofield.domain.rds.entity.userPush;

import com.gofield.domain.rds.entity.userPush.repository.UserPushRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPushRepository extends JpaRepository<UserPush, Long>, UserPushRepositoryCustom {
}
