package com.gofield.domain.rds.domain.admin.repository;

import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import com.gofield.domain.rds.domain.admin.projection.QAdminInfoProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.admin.QAdmin.admin;
import static com.gofield.domain.rds.domain.admin.QAdminRole.adminRole;

@RequiredArgsConstructor
public class AdminRepositoryCustomImpl implements AdminRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Admin findByUsername(String username) {
        return jpaQueryFactory
                .selectFrom(admin)
                .where(admin.username.eq(username))
                .fetchOne();
    }

    @Override
    public Admin findByAdminId(Long adminId) {
        return jpaQueryFactory
                .selectFrom(admin)
                .where(admin.id.eq(adminId))
                .fetchOne();
    }

    @Override
    public Page<AdminInfoProjection> findAllAdminInfoList(Pageable pageable) {
        List<AdminInfoProjection> content = jpaQueryFactory
                .select(new QAdminInfoProjection(
                        admin.id,
                        admin.name,
                        admin.username,
                        admin.password,
                        admin.tel,
                        adminRole.role,
                        admin.createDate))
                .from(admin)
                .innerJoin(adminRole)
                .on(admin.adminRole.id.eq(adminRole.id))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(admin.id.desc())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(admin.id)
                .from(admin)
                .innerJoin(adminRole)
                .on(admin.adminRole.id.eq(adminRole.id))
                .fetch();

        return new PageImpl<>(content, pageable, totalCount.size());
    }

    @Override
    public AdminInfoProjection findAdminInfoProjectionById(Long id) {
        return jpaQueryFactory
                .select(new QAdminInfoProjection(
                        admin.id,
                        admin.name,
                        admin.username,
                        admin.password,
                        admin.tel,
                        adminRole.role,
                        admin.createDate))
                .from(admin)
                .innerJoin(adminRole)
                .on(admin.adminRole.id.eq(adminRole.id))
                .where(admin.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Admin> findAllList(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(admin)
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
                .orderBy(admin.id.desc())
                .fetch();
    }
}
