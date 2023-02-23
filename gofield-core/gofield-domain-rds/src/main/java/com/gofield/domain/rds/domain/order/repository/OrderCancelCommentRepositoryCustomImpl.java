package com.gofield.domain.rds.domain.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderCancelCommentRepositoryCustomImpl implements OrderCancelCommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


}
