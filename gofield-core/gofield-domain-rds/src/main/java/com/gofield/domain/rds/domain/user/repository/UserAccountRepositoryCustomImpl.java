package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.user.QUserAccount.userAccount;


@RequiredArgsConstructor
public class UserAccountRepositoryCustomImpl implements UserAccountRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserAccount findByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(userAccount)
                .where(userAccount.user.id.eq(userId))
                .fetchOne();
    }
}
