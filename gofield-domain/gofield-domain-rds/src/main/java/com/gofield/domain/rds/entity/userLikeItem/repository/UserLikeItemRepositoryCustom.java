package com.gofield.domain.rds.entity.userLikeItem.repository;


import com.gofield.domain.rds.entity.userLikeItem.UserLikeItem;

public interface UserLikeItemRepositoryCustom {
    UserLikeItem findByUserIdAndItemId(Long userId, Long itemId);
}
