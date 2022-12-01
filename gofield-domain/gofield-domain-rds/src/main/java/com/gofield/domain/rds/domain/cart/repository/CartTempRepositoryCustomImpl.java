package com.gofield.domain.rds.domain.cart.repository;

import com.gofield.domain.rds.domain.cart.CartTemp;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.domain.cart.QCartTemp.cartTemp;

@RequiredArgsConstructor
public class CartTempRepositoryCustomImpl implements CartTempRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public CartTemp findByUserIdAndUuid(Long userId, String uuid) {
        return jpaQueryFactory
                .select(cartTemp)
                .from(cartTemp)
                .where(cartTemp.userId.eq(userId),
                        cartTemp.uuid.eq(uuid))
                .fetchFirst();
    }
}
