package com.gofield.domain.rds.entity.userToken.repository;

import com.gofield.domain.rds.entity.userToken.UserToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import java.util.List;

import static com.gofield.domain.rds.entity.userToken.QUserToken.userToken;
@RequiredArgsConstructor
public class UserTokenRepositoryCustomImpl implements UserTokenRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserToken findByRefreshToken(String refreshToken) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.refreshToken.eq(refreshToken))
                .fetchOne();
    }

    @Override
    public UserToken findByAccessId(Long accessId) {
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
    public List<UserToken> findByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.user.id.eq(userId))
                .fetch();
    }
}
