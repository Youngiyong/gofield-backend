package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.common.EStatusFlag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    User findByIdAndStatusActive(Long userId);
    User findByUuidAndStatus(String uuid, EStatusFlag status);

    User findByUserId(Long userId);
    User findByUserIdAndStatusActive(Long userId);

    User findByUuid(String uuid);
    Long findUserIdByUuid(String uuid);
    User findByUuidAndStatusActive(String uuid);
    Long findIdByUuidAndStatusActive(String uuid);
    User findByUserIdAndNotDeleteUser(Long userId);

    int findUserActiveTotalCount();
    int findUserDeleteTotalCount();
    int findUserTotalCount();
    Page<User> findAllByKeyword(String keyword, Pageable pageable);
}
