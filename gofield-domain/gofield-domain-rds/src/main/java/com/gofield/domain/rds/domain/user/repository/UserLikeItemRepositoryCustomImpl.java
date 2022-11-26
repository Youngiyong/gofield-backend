package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserLikeItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.gofield.domain.rds.domain.user.QUserLikeItem.userLikeItem;


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
