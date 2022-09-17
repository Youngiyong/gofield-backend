package com.gofield.domain.rds.entity.userAccountSmsHistory.repository;

import com.gofield.domain.rds.entity.userAccountSmsHistory.UserAccountSmsHistory;

import java.util.List;

public interface UserAccountSmsHistoryRepositoryCustom {
    UserAccountSmsHistory findByUserIdAndCode(Long userId, String code);
    List<Long> todaySmsAccountCount(Long userId);
}
