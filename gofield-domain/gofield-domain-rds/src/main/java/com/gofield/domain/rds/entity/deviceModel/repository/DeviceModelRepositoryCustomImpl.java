package com.gofield.domain.rds.entity.deviceModel.repository;

import com.gofield.domain.rds.entity.deviceModel.DeviceModel;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.deviceModel.QDeviceModel.deviceModel;

@RequiredArgsConstructor
public class DeviceModelRepositoryCustomImpl implements DeviceModelRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public DeviceModel findByModel(String model) {
        return jpaQueryFactory
                .selectFrom(deviceModel)
                .where(deviceModel.model.eq(model))
                .fetchFirst();
    }
}
