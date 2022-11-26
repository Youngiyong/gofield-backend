package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.user.QUserAddress.userAddress;


@RequiredArgsConstructor
public class UserAddressRepositoryCustomImpl implements UserAddressRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserAddress findByIdAndUserId(Long id, Long userId) {
        return jpaQueryFactory
                .select(userAddress)
                .from(userAddress)
                .where(userAddress.id.eq(id),
                        userAddress.user.id.eq(userId))
                .fetchOne();
    }

}
