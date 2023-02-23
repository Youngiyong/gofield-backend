package com.gofield.domain.rds.domain.banner.repository;

import com.gofield.domain.rds.domain.banner.Banner;
import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.gofield.domain.rds.domain.banner.QBanner.banner;

@RequiredArgsConstructor
public class BannerRepositoryCustomImpl implements BannerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Banner> findAllOrderBySort() {
        return jpaQueryFactory
                .selectFrom(banner)
                .where(banner.deleteDate.isNull())
                .orderBy(banner.sort.asc())
                .fetch();
    }

    @Override
    public Page<Banner> findAllPaging(Pageable pageable) {
        List<Banner> content = jpaQueryFactory
                .selectFrom(banner)
                .where(banner.deleteDate.isNull())
                .orderBy(banner.createDate.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(banner.id)
                .from(banner)
                .where(banner.deleteDate.isNull())
                .fetch();

        return new PageImpl<>(content, pageable, totalCount.size());
    }

    @Override
    public Banner findByBannerId(Long bannerId) {
        return jpaQueryFactory
                .selectFrom(banner)
                .where(banner.id.eq(bannerId))
                .fetchFirst();
    }
}
