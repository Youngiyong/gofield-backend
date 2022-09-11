package com.gofield.domain.rds.entity.userToken.repository;

import com.gofield.domain.rds.entity.userToken.UserToken;

import java.util.List;

public interface UserTokenRepositoryCustom {
    List<UserToken> findByUserId(Long userId);

    UserToken findByRefreshToken(String refreshToken);
    void delete(List<Long> id);
}
