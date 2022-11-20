package com.gofield.domain.rds.entity.userLikeItem.repository;

import com.gofield.domain.rds.entity.userLikeItem.UserLikeItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import static com.gofield.domain.rds.entity.userLikeItem.QUserLikeItem.userLikeItem;

@RequiredArgsConstructor
public class UserLikeItemRepositoryCustomImpl implements UserLikeItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserLikeItem findByUserIdAndItemId(Long userId, Long itemId) {
        return jpaQueryFactory
                .selectFrom(userLikeItem)
                .where(userLikeItem.user.id.eq(userId),
                        userLikeItem.item.id.eq(itemId))
                .fetchOne();
    }
}
