package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemRecent;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItemRecent.itemRecent;
import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;

@RequiredArgsConstructor
public class ItemRecentRepositoryCustomImpl implements ItemRecentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ItemRecent findByItemNumberAndUserId(String itemNumber, Long userId) {
        return jpaQueryFactory
                .selectFrom(itemRecent)
                .where(itemRecent.itemNumber.eq(itemNumber), itemRecent.userId.eq(userId))
                .fetchFirst();
    }
}
