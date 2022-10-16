package com.gofield.domain.rds.entity.userToken;

import com.gofield.domain.rds.entity.userToken.repository.UserTokenRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long>, UserTokenRepositoryCustom {
}
