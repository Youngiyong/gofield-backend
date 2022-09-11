package com.gofield.domain.rds.entity.device;

import com.gofield.domain.rds.entity.device.repository.DeviceRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.device.QDevice.device;
@RequiredArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Device findByDeviceKey(String deviceKey) {
        return jpaQueryFactory
                .selectFrom(device)
                .where(device.deviceKey.eq(deviceKey))
                .fetchOne();
    }
}
