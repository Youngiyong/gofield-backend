package com.gofield.domain.rds.entity.userToken.repository;

import com.gofield.domain.rds.entity.userToken.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long>, UserTokenRepositoryCustom {
}
