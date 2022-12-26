package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.seller.Seller;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.seller.QSeller.seller;


@RequiredArgsConstructor
public class SellerRepositoryCustomImpl implements SellerRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Seller findBySellerId(Long sellerId) {
        return jpaQueryFactory
                .selectFrom(seller)
                .where(seller.id.eq(sellerId))
                .fetchFirst();
    }
}
