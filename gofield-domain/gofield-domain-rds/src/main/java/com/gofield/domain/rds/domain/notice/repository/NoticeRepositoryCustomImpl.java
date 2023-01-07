package com.gofield.domain.rds.domain.notice.repository;

import com.gofield.domain.rds.domain.notice.Notice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.notice.QNotice.notice;

@RequiredArgsConstructor
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression eqVisible(Boolean isVisible){
        if (isVisible == null || !isVisible) {
            return notice.isVisible.isFalse();
        }
        return null;
    }
    @Override
    public Page<Notice> findAllPaging(Pageable pageable, Boolean isVisible) {
        List<Notice> result = jpaQueryFactory
                .selectFrom(notice)
                .from(notice)
                .where(eqVisible(isVisible), notice.deleteDate.isNull())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(notice.id.desc())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(notice.id)
                .from(notice)
                .where(eqVisible(isVisible), notice.deleteDate.isNull())
                .fetch();

        return new PageImpl<>(result, pageable, totalCount.size());
    }

    @Override
    public Notice findByNoticeId(Long noticeId) {
        return jpaQueryFactory
                .selectFrom(notice)
                .where(notice.id.eq(noticeId), notice.deleteDate.isNull())
                .fetchFirst();
    }
}
