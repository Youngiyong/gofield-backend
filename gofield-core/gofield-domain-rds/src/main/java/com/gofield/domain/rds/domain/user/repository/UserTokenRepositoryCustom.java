package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserToken;

public interface UserTokenRepositoryCustom {

    UserToken findByAccessToken(String accessToken);
    UserToken findByRefreshToken(String refreshToken);
}
