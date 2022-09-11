package com.gofield.domain.rds.entity.userAddress;

import com.gofield.domain.rds.entity.userAddress.repository.UserAddressRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAddressRepositoryImpl implements UserAddressRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
