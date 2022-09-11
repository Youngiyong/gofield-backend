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
    public void delete(List<Long> id) {
        jpaQueryFactory
                .delete(userToken)
                .where(userToken.id.in(id))
                .execute();
    }

    @Override
    public List<UserToken> findByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(userToken)
                .where(userToken.userId.eq(userId))
                .fetch();
    }
}
