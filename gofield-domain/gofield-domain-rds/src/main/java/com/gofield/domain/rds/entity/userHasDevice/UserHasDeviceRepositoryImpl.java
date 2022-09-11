package com.gofield.domain.rds.entity.userHasDevice;

import com.gofield.domain.rds.entity.userHasDevice.repository.UserHasDeviceRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userHasDevice.QUserHasDevice.userHasDevice;
import static com.gofield.domain.rds.entity.device.QDevice.device;

@RequiredArgsConstructor
public class UserHasDeviceRepositoryImpl implements UserHasDeviceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserHasDevice findByUserIdAndDeviceId(Long userId, Long deviceId) {
        return jpaQueryFactory
                .selectFrom(userHasDevice)
                .where(userHasDevice.user.id.eq(userId)
                        .and(userHasDevice.device.id.eq(deviceId)))
                .fetchOne();
    }

    @Override
    public UserHasDevice findByUserIdAndDeviceKey(Long userId, String deviceKey) {
        return jpaQueryFactory
                .selectFrom(userHasDevice)
                .innerJoin(device)
                .on(userHasDevice.device.id.eq(device.id))
                .where(userHasDevice.user.id.eq(userId)
                        .and(device.deviceKey.eq(deviceKey)))
                .fetchOne();
    }
}
