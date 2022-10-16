package com.gofield.domain.rds.entity.userWebToken;

import com.gofield.domain.rds.entity.userWebToken.repository.UserWebTokenRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWebWebTokenRepository extends JpaRepository<UserWebToken, Long>, UserWebTokenRepositoryCustom {
}
