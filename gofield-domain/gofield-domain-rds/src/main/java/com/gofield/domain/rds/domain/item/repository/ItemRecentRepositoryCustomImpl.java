package com.gofield.domain.rds.domain.item.repository;

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
    public List<Long> findByUserId(Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(itemRecent.itemId.count())
                .from(itemRecent)
                .innerJoin(itemStock)
                .on(itemRecent.itemNumber.eq(itemStock.itemNumber))
                .where(itemRecent.userId.eq(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .groupBy(itemRecent.itemId)
                .having(itemRecent.itemId.count().goe(1))
                .fetch();
    }
}
