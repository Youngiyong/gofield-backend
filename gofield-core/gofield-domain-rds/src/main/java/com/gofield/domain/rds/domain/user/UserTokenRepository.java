package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserTokenRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long>, UserTokenRepositoryCustom {
}
