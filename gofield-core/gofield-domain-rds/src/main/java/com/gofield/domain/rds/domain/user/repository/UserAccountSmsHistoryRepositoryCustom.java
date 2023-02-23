package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserAccountSmsHistory;

import java.util.List;

public interface UserAccountSmsHistoryRepositoryCustom {
    UserAccountSmsHistory findByUserIdAndCode(Long userId, String code);
    List<Long> todaySmsAccountCount(Long userId);
}
