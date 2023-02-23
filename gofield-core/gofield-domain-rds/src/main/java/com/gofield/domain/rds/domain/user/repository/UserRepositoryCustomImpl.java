package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.common.EStatusFlag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression containNameAndUsername(String keyword) {
        if (keyword == null) {
            return null;
        }
        return user.name.contains(keyword).or(user.nickName.contains(keyword));
    }

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
    public User findByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public User findByUserIdAndStatusActive(Long userId) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.id.eq(userId), user.status.eq(EStatusFlag.ACTIVE))
                .fetchFirst();
    }

    @Override
    public User findByUuid(String uuid) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.uuid.eq(uuid))
                .fetchOne();
    }

    @Override
    public Long findUserIdByUuid(String uuid) {
        return jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(user.uuid.eq(uuid))
                .fetchFirst();
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
    public User findByUserIdAndNotDeleteUser(Long userId) {
        return jpaQueryFactory
                .selectFrom(user)
                .where(user.id.eq(userId), user.status.ne(EStatusFlag.DELETE))
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

    @Override
    public Page<User> findAllByKeyword(String keyword, Pageable pageable) {
        List<User> content = jpaQueryFactory
                .select(user)
                .from(user)
                .where(user.status.ne(EStatusFlag.DELETE), containNameAndUsername((keyword)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(user.id.desc())
                .fetch();

        List<Long> totalCount = jpaQueryFactory
                .select(user.id)
                .from(user)
                .where(user.status.ne(EStatusFlag.DELETE), containNameAndUsername((keyword)))
                .fetch();

        return new PageImpl<>(content, pageable, totalCount.size());
    }

}
