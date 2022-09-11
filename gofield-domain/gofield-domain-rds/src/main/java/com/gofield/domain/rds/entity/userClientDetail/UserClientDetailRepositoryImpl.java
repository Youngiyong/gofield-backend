package com.gofield.domain.rds.entity.userClientDetail;

import com.gofield.domain.rds.entity.userClientDetail.repository.UserClientDetailRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.entity.userClientDetail.QUserClientDetail.userClientDetail;
@RequiredArgsConstructor
public class UserClientDetailRepositoryImpl implements UserClientDetailRepositoryCustom {

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
