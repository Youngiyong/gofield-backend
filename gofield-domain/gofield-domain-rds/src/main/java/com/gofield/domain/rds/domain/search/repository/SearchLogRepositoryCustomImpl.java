package com.gofield.domain.rds.domain.search.repository;

import com.gofield.domain.rds.domain.search.SearchLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.search.QSearchLog.searchLog;

@RequiredArgsConstructor
public class SearchLogRepositoryCustomImpl implements SearchLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<SearchLog> findByUserIdLimit(Long userId, int limit) {
        return jpaQueryFactory
                .selectFrom(searchLog)
                .where(searchLog.userId.eq(userId))
                .orderBy(searchLog.id.desc())
                .limit(limit)
                .fetch();
    }
}
