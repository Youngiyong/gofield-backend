package com.gofield.domain.rds.domain.item.repository;


import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.item.ItemStock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import java.util.List;

import static com.gofield.domain.rds.domain.item.QItem.item;
import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;
import static com.gofield.domain.rds.domain.item.QShippingTemplate.shippingTemplate;

@RequiredArgsConstructor
public class ItemStockRepositoryCustomImpl implements ItemStockRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ItemStock findByItemNumber(String itemNumber) {
        return jpaQueryFactory
                .select(itemStock)
                .from(itemStock)
                .where(itemStock.itemNumber.eq(itemNumber))
                .fetchFirst();
    }

    @Override
    public ItemStock findItemByItemNumber(String itemNumber) {
        return jpaQueryFactory
                .select(itemStock)
                .from(itemStock)
                .innerJoin(itemStock.item, item).fetchJoin()
                .where(itemStock.itemNumber.eq(itemNumber))
                .fetchFirst();
    }

    @Override
    public List<ItemStock> findAllInItemNumber(List<String> itemNumberList) {
        return jpaQueryFactory
                .select(itemStock)
                .from(itemStock)
                .where(itemStock.itemNumber.in(itemNumberList))
                .fetch();
    }

    @Override
    public ItemStock findShippingTemplateByItemNumberFetch(String itemNumber) {
        return jpaQueryFactory
                .selectFrom(itemStock)
                .innerJoin(itemStock.item, item).fetchJoin()
                .innerJoin(item.shippingTemplate, shippingTemplate).fetchJoin()
                .where(itemStock.itemNumber.eq(itemNumber))
                .fetchOne();
    }

    @Override
    public void deleteIdList(List<Long> idList, Long itemId) {
        jpaQueryFactory
                .delete(itemStock)
                .where(itemStock.id.in(idList), itemStock.item.id.eq(itemId))
                .execute();
    }

}
