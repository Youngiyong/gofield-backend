package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.ItemStock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;

@RequiredArgsConstructor
public class ItemStockRepositoryCustomImpl implements ItemStockRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ItemStock findByItemNumber(String itemNumber) {
        return jpaQueryFactory
                .selectFrom(itemStock)
                .where(itemStock.itemNumber.eq(itemNumber))
                .fetchFirst();
    }
}
