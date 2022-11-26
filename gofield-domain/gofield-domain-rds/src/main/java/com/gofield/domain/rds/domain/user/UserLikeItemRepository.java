package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserLikeItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeItemRepository extends JpaRepository<UserLikeItem, Long>, UserLikeItemRepositoryCustom {
}
