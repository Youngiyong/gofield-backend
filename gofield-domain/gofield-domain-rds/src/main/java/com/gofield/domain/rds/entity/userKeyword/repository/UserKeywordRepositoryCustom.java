package com.gofield.domain.rds.entity.userKeyword.repository;


import com.gofield.domain.rds.entity.userKeyword.UserKeyword;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserKeywordRepositoryCustom {
    UserKeyword findByUserIdAndKeyword(Long userId, String keyword);
    List<UserKeyword> findByUserId(Long userId, Pageable pageable);

}
