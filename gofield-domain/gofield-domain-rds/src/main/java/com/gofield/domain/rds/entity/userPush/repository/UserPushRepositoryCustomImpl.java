package com.gofield.domain.rds.entity.userPush.repository;

import com.gofield.domain.rds.entity.userPush.UserPush;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userPush.QUserPush.userPush;

@RequiredArgsConstructor
public class UserPushRepositoryCustomImpl implements UserPushRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserPush findByPlatformAndPushKey(EPlatformFlag platform, String pushKey) {
        return jpaQueryFactory
                .selectFrom(userPush)
                .where(userPush.platform.eq(platform),
                        userPush.pushKey.eq(pushKey))
                .fetchOne();
    }
}
