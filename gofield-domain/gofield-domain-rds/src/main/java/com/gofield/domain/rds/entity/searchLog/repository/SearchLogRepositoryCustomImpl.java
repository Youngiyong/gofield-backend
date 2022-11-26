package com.gofield.domain.rds.entity.searchLog.repository;

import com.gofield.domain.rds.entity.searchLog.SearchLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RequiredArgsConstructor
public class SearchLogRepositoryCustomImpl implements SearchLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;



}
