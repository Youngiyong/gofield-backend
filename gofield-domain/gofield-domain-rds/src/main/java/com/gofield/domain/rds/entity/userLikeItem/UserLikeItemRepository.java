package com.gofield.domain.rds.entity.userLikeItem;

import com.gofield.domain.rds.entity.userLikeItem.repository.UserLikeItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeItemRepository extends JpaRepository<UserLikeItem, Long>, UserLikeItemRepositoryCustom {
}
