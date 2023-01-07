package com.gofield.domain.rds.domain.notice.repository;

import com.gofield.domain.rds.domain.notice.Notice;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Notice> findAllOrderBySort() {
        return null;
    }

    @Override
    public Page<Notice> findAllPaging(Pageable pageable) {
        return null;
    }

    @Override
    public Notice findByNoticeId(Long noticeId) {
        return null;
    }
}
