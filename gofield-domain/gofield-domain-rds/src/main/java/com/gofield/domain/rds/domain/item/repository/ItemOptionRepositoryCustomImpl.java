package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;
import com.gofield.domain.rds.domain.item.projection.QItemOptionProjection;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItemOption.itemOption;
import static com.gofield.domain.rds.domain.item.QItemStock.itemStock;

@RequiredArgsConstructor
public class ItemOptionRepositoryCustomImpl implements ItemOptionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemOptionProjection> findAllItemOptionByItemId(Long itemId) {
        return jpaQueryFactory
                .select(new QItemOptionProjection(
                        itemOption.id,
                        itemOption.item.id.as("itemId"),
                        itemOption.itemNumber,
                        itemOption.name,
                        itemOption.optionType,
                        itemStock.status,
                        itemOption.price,
                        itemOption.optionPrice,
                        itemStock.qty,
                        itemOption.sort,
                        itemOption.isUse,
                        itemOption.createDate))
                .from(itemOption)
                .innerJoin(itemStock)
                .on(itemOption.itemNumber.eq(itemStock.itemNumber))
                .where(itemOption.item.id.eq(itemId))
                .fetch();
    }

    @Override
    public ItemOption findByItemNumber(String itemNumber) {
        return jpaQueryFactory
                .selectFrom(itemOption)
                .where(itemOption.itemNumber.eq(itemNumber))
                .fetchFirst();
    }

    @Override
    public ItemOption findByOptionId(Long id) {
        return jpaQueryFactory
                .select(itemOption)
                .where(itemOption.id.eq(id))
                .fetchFirst();
    }
}
