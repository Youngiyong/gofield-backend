package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.user.QUser.user;

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

    @Override
    public int findUserActiveTotalCount() {
        List<Long> userList = jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(user.status.eq(EStatusFlag.ACTIVE))
                .fetch();

        return userList.size();
    }

    @Override
    public int findUserDeleteTotalCount() {
        List<Long> userList = jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(user.status.eq(EStatusFlag.DELETE))
                .fetch();

        return userList.size();
    }

    @Override
    public int findUserTotalCount() {
        List<Long> userList = jpaQueryFactory
                .select(user.id)
                .from(user)
                .fetch();

        return userList.size();
    }

}
