package com.gofield.domain.rds.entity.userAddress.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAddressRepositoryCustomImpl implements UserAddressRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

}
