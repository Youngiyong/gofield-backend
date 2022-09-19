package com.gofield.domain.rds.entity.topKeyword.repository;

import com.gofield.domain.rds.entity.topKeyword.TopKeyword;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.gofield.domain.rds.entity.topKeyword.QTopKeyword.topKeyword;

@RequiredArgsConstructor
public class TopKeywordRepositoryCustomImpl implements TopKeywordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public TopKeyword findByKeyword(String keyword) {
        return jpaQueryFactory
                .selectFrom(topKeyword)
                .where(topKeyword.keyword.eq(keyword))
                .fetchOne();
    }

    @Override
    public List<TopKeyword> findTopKeyword(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(topKeyword)
                .where(topKeyword.createDate.between(LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(topKeyword.count.desc())
                .fetch();
    }

}
