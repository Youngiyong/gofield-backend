package com.gofield.domain.rds.entity.userWebPush;

import com.gofield.domain.rds.entity.userWebPush.repository.UserWebPushRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWebWebPushRepository extends JpaRepository<UserWebPush, Long>, UserWebPushRepositoryCustom {
}
