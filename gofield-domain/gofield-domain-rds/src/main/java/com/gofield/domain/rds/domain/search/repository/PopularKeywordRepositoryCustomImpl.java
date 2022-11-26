package com.gofield.domain.rds.domain.search.repository;

import com.gofield.domain.rds.domain.search.PopularKeyword;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.search.QPopularKeyword.popularKeyword;


@RequiredArgsConstructor
public class PopularKeywordRepositoryCustomImpl implements PopularKeywordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<PopularKeyword> findAllPopularKeywordList(int size) {
        return jpaQueryFactory
                .selectFrom(popularKeyword)
                .limit(size)
                .orderBy(popularKeyword.sort.asc())
                .fetch();
    }
}
