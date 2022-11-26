package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserSns;
import com.gofield.domain.rds.domain.user.ESocialFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.user.QUserSns.userSns;


@RequiredArgsConstructor
public class UserSnsRepositoryCustomImpl implements UserSnsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findIdListByUserId(Long userId) {
        return jpaQueryFactory
                .select(userSns.id)
                .from(userSns)
                .where(userSns.user.id.eq(userId))
                .fetch();
    }

    @Override
    public void withdraw(List<Long> userIdList) {
        jpaQueryFactory
                .delete(userSns)
                .where(userSns.id.in(userIdList))
                .execute();
    }

    @Override
    public UserSns findByUniQueIdAndRoute(String uniqueId, ESocialFlag route) {
        return jpaQueryFactory
                .selectFrom(userSns)
                .where(userSns.uniqueId.eq(uniqueId)
                        .and(userSns.social.eq(route)))
                .fetchOne();
    }
}
