package com.gofield.domain.rds.entity.userWebPush.repository;

import com.gofield.domain.rds.entity.userWebPush.UserWebPush;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userWebPush.QUserWebPush.userWebPush;

@RequiredArgsConstructor
public class UserWebPushRepositoryCustomImpl implements UserWebPushRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserWebPush findByUserIdAndPushKey(Long userId, String pushKey) {
        return jpaQueryFactory
                .selectFrom(userWebPush)
                .where(userWebPush.user.id.eq(userId),
                        userWebPush.pushKey.eq(pushKey))
                .fetchOne();
    }
}
