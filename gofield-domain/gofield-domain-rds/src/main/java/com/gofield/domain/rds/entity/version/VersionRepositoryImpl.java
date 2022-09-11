package com.gofield.domain.rds.entity.version;

import com.gofield.domain.rds.entity.version.repository.VersionRepositoryCustom;
import com.gofield.domain.rds.enums.EPlatformFlag;
import com.gofield.domain.rds.enums.EClientType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.entity.version.QVersion.version;
@RequiredArgsConstructor
public class VersionRepositoryImpl implements VersionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Version findByPlatformAndType(EPlatformFlag platform, EClientType type) {
        return jpaQueryFactory
                .selectFrom(version)
                .where(version.platform.eq(platform).and(version.type.eq(type)))
                .fetchOne();
    }
}
