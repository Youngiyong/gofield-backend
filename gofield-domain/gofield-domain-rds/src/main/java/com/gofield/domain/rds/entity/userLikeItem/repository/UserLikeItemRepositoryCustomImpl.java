package com.gofield.domain.rds.entity.userLikeItem.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLikeItemRepositoryCustomImpl implements UserLikeItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
