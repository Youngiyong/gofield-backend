package com.gofield.domain.rds.entity.userWebToken.repository;

import com.gofield.domain.rds.entity.userWebToken.UserWebToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import java.util.List;

import static com.gofield.domain.rds.entity.userWebToken.QUserToken.userToken;
@RequiredArgsConstructor
public class UserWebTokenRepositoryCustomImpl implements UserWebTokenRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserWebToken findByRefreshToken(String refreshToken) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.refreshToken.eq(refreshToken))
                .fetchOne();
    }

    @Override
    public UserWebToken findByAccessId(Long accessId) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.access.id.eq(accessId))
                .fetchOne();
    }

    @Override
    public void delete(Long accessId) {
        jpaQueryFactory
                .delete(userToken)
                .where(userToken.access.id.eq(accessId))
                .execute();
    }



    @Override
    public List<UserWebToken> findByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.user.id.eq(userId))
                .fetch();
    }
}
