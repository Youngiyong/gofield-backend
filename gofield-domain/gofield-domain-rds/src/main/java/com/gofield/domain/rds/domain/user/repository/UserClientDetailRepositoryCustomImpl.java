package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserClientDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.user.QUserClientDetail.userClientDetail;


@RequiredArgsConstructor
public class UserClientDetailRepositoryCustomImpl implements UserClientDetailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserClientDetail findByClientId(String clientId) {
        return jpaQueryFactory
                .selectFrom(userClientDetail)
                .where(userClientDetail.clientId.eq(clientId))
                .fetchOne();
    }

    @Override
    public UserClientDetail findByClientId(Long id) {
        return jpaQueryFactory
                .selectFrom(userClientDetail)
                .where(userClientDetail.id.eq(id))
                .fetchFirst();
    }
}
