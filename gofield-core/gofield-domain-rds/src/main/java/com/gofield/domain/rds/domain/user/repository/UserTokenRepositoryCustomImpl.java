package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.user.QUserToken.userToken;

@RequiredArgsConstructor
public class UserTokenRepositoryCustomImpl implements UserTokenRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserToken findByAccessToken(String accessToken) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.accessToken.eq(accessToken))
                .fetchOne();
    }

    @Override
    public UserToken findByRefreshToken(String refreshToken) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.refreshToken.eq(refreshToken))
                .fetchOne();
    }
}
