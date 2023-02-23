package com.gofield.domain.rds.domain.user.repository;


import com.gofield.domain.rds.domain.user.UserLikeItem;

public interface UserLikeItemRepositoryCustom {
    UserLikeItem findByUserIdAndItemId(Long userId, Long itemId);
}
