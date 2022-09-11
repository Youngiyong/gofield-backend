package com.gofield.domain.rds.entity.userSns;

import com.gofield.domain.rds.entity.userSns.repository.UserSnsRepositoryCustom;
import com.gofield.domain.rds.enums.ESocialFlag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userSns.QUserSns.userSns;

@RequiredArgsConstructor
public class UserSnsRepositoryImpl implements UserSnsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserSns findByUniQueIdAndRoute(String uniqueId, ESocialFlag route) {
        return jpaQueryFactory
                .selectFrom(userSns)
                .where(userSns.uniqueId.eq(uniqueId)
                        .and(userSns.route.eq(route)))
                .fetchOne();
    }
}
