package com.gofield.domain.rds.entity.userWebToken.repository;

import com.gofield.domain.rds.entity.userWebToken.UserWebToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userWebToken.QUserWebToken.userWebToken;

import java.util.List;

@RequiredArgsConstructor
public class UserWebTokenRepositoryCustomImpl implements UserWebTokenRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserWebToken findByAccessToken(String accessToken) {
        return jpaQueryFactory
                .selectFrom(userWebToken)
                .where(userWebToken.accessToken.eq(accessToken))
                .fetchOne();
    }

    @Override
    public UserWebToken findByRefreshToken(String refreshToken) {
        return jpaQueryFactory
                .selectFrom(userWebToken)
                .where(userWebToken.refreshToken.eq(refreshToken))
                .fetchOne();
    }
}
