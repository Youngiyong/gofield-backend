package com.gofield.domain.rds.entity.userWebPush.repository;

import com.gofield.domain.rds.entity.userWebPush.UserWebPush;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userWebPush.QUserPush.userPush;

@RequiredArgsConstructor
public class UserWebPushRepositoryCustomImpl implements UserWebPushRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserWebPush findByUserIdAndPushKey(Long userId, String pushKey) {
        return jpaQueryFactory
                .selectFrom(userPush)
                .where(userPush.user.id.eq(userId),
                        userPush.pushKey.eq(pushKey))
                .fetchOne();
    }
}
