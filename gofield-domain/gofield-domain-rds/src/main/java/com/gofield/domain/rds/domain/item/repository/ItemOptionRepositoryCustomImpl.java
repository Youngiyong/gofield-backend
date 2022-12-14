package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.ItemOptionGroup;
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
                        itemOption.viewName,
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
                .where(itemOption.item.id.eq(itemId), itemOption.deleteDate.isNull())
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
    public ItemOption findByItemIdAndItemNumber(Long itemId, String itemNumber) {
        return jpaQueryFactory
                .selectFrom(itemOption)
                .where(itemOption.item.id.eq(itemId), itemOption.itemNumber.eq(itemNumber))
                .fetchOne();
    }

    @Override
    public List<ItemOption> findAllByItemIdAndInItemNumber(Long itemId, List<String> itemNumber) {
        return jpaQueryFactory
                .selectFrom(itemOption)
                .where(itemOption.item.id.eq(itemId), itemOption.itemNumber.in(itemNumber))
                .fetch();
    }

    @Override
    public ItemOption findByOptionId(Long id) {
        return jpaQueryFactory
                .selectFrom(itemOption)
                .where(itemOption.id.eq(id))
                .fetchFirst();
    }

    @Override
    public void deleteByItemIdAndInIdList(Long itemId, List<Long> idList) {
        jpaQueryFactory
                .delete(itemOption)
                .where(itemOption.id.in(idList), itemOption.item.id.eq(itemId))
                .execute();
    }
}
