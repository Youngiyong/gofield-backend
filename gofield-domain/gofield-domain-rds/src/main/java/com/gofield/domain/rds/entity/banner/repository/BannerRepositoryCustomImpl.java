package com.gofield.domain.rds.entity.banner.repository;

import com.gofield.domain.rds.entity.banner.Banner;
import com.gofield.domain.rds.enums.EStatusFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.gofield.domain.rds.entity.banner.QBanner.banner;

@RequiredArgsConstructor
public class BannerRepositoryCustomImpl implements BannerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Banner> findAllActive() {
        return jpaQueryFactory
                .selectFrom(banner)
                .where(banner.status.eq(EStatusFlag.ACTIVE)
                        ,banner.startDate.before(LocalDateTime.now()),
                                banner.endDate.after(LocalDateTime.now()))
                .fetch();
    }
}
