package com.gofield.domain.rds.entity.itemQna.repository;


import com.gofield.domain.rds.entity.itemQna.ItemQna;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gofield.domain.rds.entity.itemQna.QItemQna.itemQna;

@RequiredArgsConstructor
public class ItemQnaRepositoryCustomImpl implements ItemQnaRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression eqUserId(Long userId){
        if(userId == null){
            return null;
        }
        return itemQna.user.id.eq(userId);
    }

    private BooleanExpression eqVisible(Boolean isVisible){
        if(isVisible==null){
            return null;
        }
        return itemQna.isVisible.eq(isVisible);
    }

    @Override
    public List<ItemQna> findAllByItemIdAndUserId(Long itemId, Long userId, Pageable pageable) {
        return jpaQueryFactory
                .select(itemQna)
                .from(itemQna)
                .where(itemQna.item.id.eq(itemId),
                        eqUserId(userId))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public ItemQna findByQnaIdAndItemId(Long qnaId, Long itemId) {
        return jpaQueryFactory
                .select(itemQna)
                .from(itemQna)
                .where(itemQna.id.eq(qnaId),
                        itemQna.item.id.eq(itemId))
                .fetchOne();
    }

    @Override
    public ItemQna findByQnaIdAndUserId(Long qnaId, Long userId) {
        return jpaQueryFactory
                .selectFrom(itemQna)
                .where(itemQna.id.eq(qnaId),
                        itemQna.user.id.eq(userId))
                .fetchOne();
    }

}
