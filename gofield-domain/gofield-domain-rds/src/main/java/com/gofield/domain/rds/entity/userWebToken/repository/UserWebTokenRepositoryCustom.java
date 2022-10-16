package com.gofield.domain.rds.entity.userWebToken.repository;

import com.gofield.domain.rds.entity.userWebToken.UserWebToken;

import java.util.List;

public interface UserWebTokenRepositoryCustom {

    UserWebToken findByAccessToken(String accessToken);
    UserWebToken findByRefreshToken(String refreshToken);
}
