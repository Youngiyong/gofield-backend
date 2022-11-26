package com.gofield.domain.rds.domain.banner.repository;

import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.gofield.domain.rds.domain.banner.QBanner.banner;

@RequiredArgsConstructor
public class BannerRepositoryCustomImpl implements BannerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Banner> findAllIsActive() {
        return jpaQueryFactory
                .selectFrom(banner)
                .where(banner.status.eq(EStatusFlag.ACTIVE)
                        ,banner.startDate.before(LocalDateTime.now()),
                                banner.endDate.after(LocalDateTime.now()))
                .fetch();
    }
}
