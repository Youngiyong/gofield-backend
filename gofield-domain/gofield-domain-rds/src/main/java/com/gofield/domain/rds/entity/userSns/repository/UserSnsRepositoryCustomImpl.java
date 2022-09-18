package com.gofield.domain.rds.entity.userSns.repository;

import com.gofield.domain.rds.entity.userSns.UserSns;
import com.gofield.domain.rds.enums.ESocialFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.entity.userSns.QUserSns.userSns;

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
