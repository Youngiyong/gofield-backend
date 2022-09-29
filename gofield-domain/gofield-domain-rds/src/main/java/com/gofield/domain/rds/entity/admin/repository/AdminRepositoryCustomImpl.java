package com.gofield.domain.rds.entity.admin.repository;

import com.gofield.domain.rds.entity.admin.Admin;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.admin.QAdmin.admin;

@RequiredArgsConstructor
public class AdminRepositoryCustomImpl implements AdminRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Admin loadByUsername(String username) {
        return jpaQueryFactory
                .selectFrom(admin)
                .where(admin.username.eq(username))
                .fetchOne();
    }
}
