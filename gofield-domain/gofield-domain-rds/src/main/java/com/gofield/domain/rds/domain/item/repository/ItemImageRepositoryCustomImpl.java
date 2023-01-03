package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.item.ItemImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.item.QItemImage.itemImage;


@RequiredArgsConstructor
public class ItemImageRepositoryCustomImpl implements ItemImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public ItemImage findByIdAndItemId(Long id, Long itemId) {
        return jpaQueryFactory
                .selectFrom(itemImage)
                .where(itemImage.id.eq(id), itemImage.item.id.eq(itemId))
                .fetchOne();
    }
}
