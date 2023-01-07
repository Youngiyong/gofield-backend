package com.gofield.domain.rds.domain.faq.repository;

import com.gofield.domain.rds.domain.faq.Faq;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class FaqRepositoryCustomImpl implements FaqRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Faq> findAllOrderBySort() {
        return null;
    }

    @Override
    public Page<Faq> findAllPaging(Pageable pageable) {
        return null;
    }

    @Override
    public Faq findByNoticeId(Long noticeId) {
        return null;
    }
}
