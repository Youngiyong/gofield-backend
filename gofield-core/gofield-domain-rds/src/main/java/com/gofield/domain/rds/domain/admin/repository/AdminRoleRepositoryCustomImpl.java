package com.gofield.domain.rds.domain.admin.repository;

import com.gofield.domain.rds.domain.admin.AdminRole;
import com.gofield.domain.rds.domain.admin.EAdminRole;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.admin.QAdminRole.adminRole;

@RequiredArgsConstructor
public class AdminRoleRepositoryCustomImpl implements AdminRoleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AdminRole findByRole(EAdminRole role) {
        return jpaQueryFactory
                .selectFrom(adminRole)
                .where(adminRole.role.eq(role))
                .fetchOne();
    }
}
