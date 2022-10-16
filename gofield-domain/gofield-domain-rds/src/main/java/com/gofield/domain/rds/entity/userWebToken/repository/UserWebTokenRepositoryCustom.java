package com.gofield.domain.rds.entity.userWebToken.repository;

import com.gofield.domain.rds.entity.userWebToken.UserWebToken;

import java.util.List;

public interface UserWebTokenRepositoryCustom {
    List<UserWebToken> findByUserId(Long userId);

    UserWebToken findByRefreshToken(String refreshToken);
    UserWebToken findByAccessId(Long accessId);
    void delete(Long accessId);
}
