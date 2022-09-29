package com.gofield.domain.rds.entity.adminRole.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AdminRoleRepositoryCustomImpl implements AdminRoleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
