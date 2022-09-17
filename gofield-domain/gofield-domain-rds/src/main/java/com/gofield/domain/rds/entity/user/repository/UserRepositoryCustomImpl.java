package com.gofield.domain.rds.entity.user.repository;

import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.EStatusFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User findByIdAndStatusActive(Long userId) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.id.eq(userId).and(user.status.eq(EStatusFlag.ACTIVE)))
                .fetchOne();
    }

    @Override
    public User findByUuidAndStatus(String uuid, EStatusFlag status) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.uuid.eq(uuid)
                        .and(user.status.eq(status)))
                .fetchOne();
    }

    @Override
    public User findByUuid(String uuid) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();
    }

    @Override
    public User findByUuidAndStatusActive(String uuid) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();
    }

    @Override
    public Long findIdByUuidAndStatusActive(String uuid) {
        return jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();
    }
}
