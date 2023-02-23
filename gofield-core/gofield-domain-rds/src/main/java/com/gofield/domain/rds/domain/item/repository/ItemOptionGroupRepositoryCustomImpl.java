package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemOptionGroup;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItemOptionGroup.itemOptionGroup;

@RequiredArgsConstructor
public class ItemOptionGroupRepositoryCustomImpl implements ItemOptionGroupRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemOptionGroup> findAllItemOptionGroupByItemId(Long itemId) {
        return jpaQueryFactory
                .selectFrom(itemOptionGroup)
                .where(itemOptionGroup.item.id.eq(itemId))
                .fetch();
    }

    @Override
    public ItemOptionGroup findByGroupIdAndItemId(Long id, Long itemId) {
        return jpaQueryFactory
                .selectFrom(itemOptionGroup)
                .where(itemOptionGroup.id.eq(id), itemOptionGroup.item.id.eq(itemId))
                .fetchOne();
    }

    @Override
    public List<ItemOptionGroup> findAllByItemIdAndInIdList(List<Long> idList, Long itemId) {
        return jpaQueryFactory
                .selectFrom(itemOptionGroup)
                .where(itemOptionGroup.id.in(idList), itemOptionGroup.item.id.eq(itemId))
                .fetch();
    }

}
