package com.gofield.domain.rds.domain.cart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.cart.QCart.cart;
import static com.gofield.domain.rds.domain.user.QUser.user;
@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public int getCartCount(String uuid) {
        List<Long> cartList = jpaQueryFactory
                .select(cart.id)
                .from(cart)
                .innerJoin(user)
                .on(cart.user.id.eq(user.id))
                .where(user.uuid.eq(uuid))
                .fetch();

        return cartList.size();
    }
}
