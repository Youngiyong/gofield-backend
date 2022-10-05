package com.gofield.domain.rds.entity.adminRole.repository;

import com.gofield.domain.rds.entity.adminRole.AdminRole;
import com.gofield.domain.rds.enums.admin.EAdminRole;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.adminRole.QAdminRole.adminRole;

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
