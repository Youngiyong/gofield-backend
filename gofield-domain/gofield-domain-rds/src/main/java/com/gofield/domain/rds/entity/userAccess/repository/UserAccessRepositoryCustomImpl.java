package com.gofield.domain.rds.entity.userAccess.repository;

import com.gofield.domain.rds.entity.userAccess.UserAccess;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userAccess.QUserAccess.userAccess;

@RequiredArgsConstructor
public class UserAccessRepositoryCustomImpl implements UserAccessRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserAccess findByUserIdAndDeviceId(Long userId, Long deviceId) {
        return jpaQueryFactory
                .selectFrom(userAccess)
                .where(userAccess.user.id.eq(userId), userAccess.device.id.eq(deviceId))
                .fetchOne();
    }

    @Override
    public UserAccess findByDeviceIdAndAccessKey(Long deviceId, String accessKey) {
        return jpaQueryFactory
                .selectFrom(userAccess)
                .where(userAccess.device.id.eq(deviceId),
                        userAccess.accessKey.eq(accessKey))
                .fetchOne();
    }

    @Override
    public UserAccess findByAccessKey(String accessKey) {
        return jpaQueryFactory
                .selectFrom(userAccess)
                .where(userAccess.accessKey.eq(accessKey))
                .fetchOne();
    }
}
