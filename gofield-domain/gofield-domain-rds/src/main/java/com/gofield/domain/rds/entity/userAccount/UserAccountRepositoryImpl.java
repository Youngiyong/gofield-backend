package com.gofield.domain.rds.entity.userAccount;

import com.gofield.domain.rds.entity.userAccount.repository.UserAccountRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userAccount.QUserAccount.userAccount;

@RequiredArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserAccount findByUserId(Long userId) {
        return jpaQueryFactory
                .selectFrom(userAccount)
                .where(userAccount.user.id.eq(userId))
                .fetchOne();
    }
}
