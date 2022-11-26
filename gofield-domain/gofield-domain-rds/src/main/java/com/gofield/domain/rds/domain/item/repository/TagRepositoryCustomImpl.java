package com.gofield.domain.rds.domain.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.gofield.domain.rds.domain.item.QItemHasTag.itemHasTag;
import static com.gofield.domain.rds.domain.item.QTag.tag;


@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findAllByItemId(Long itemId) {
        return jpaQueryFactory
                .select(tag.name)
                .from(itemHasTag)
                .innerJoin(tag)
                .on(itemHasTag.tag.id.eq(tag.id))
                .where(itemHasTag.item.id.eq(itemId))
                .fetch();
    }
}
