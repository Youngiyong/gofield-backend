package com.gofield.domain.rds.entity.userAddress.repository;

import com.gofield.domain.rds.entity.userAddress.UserAddress;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.entity.userAddress.QUserAddress.userAddress;

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
