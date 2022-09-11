package com.gofield.domain.rds.entity.userAccount.repository;

import com.gofield.domain.rds.entity.userAccount.UserAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userAccount.QUserAccount.userAccount;

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
