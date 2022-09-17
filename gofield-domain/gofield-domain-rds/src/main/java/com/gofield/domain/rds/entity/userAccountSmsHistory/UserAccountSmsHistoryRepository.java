package com.gofield.domain.rds.entity.userAccountSmsHistory;

import com.gofield.domain.rds.entity.userAccountSmsHistory.repository.UserAccountSmsHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountSmsHistoryRepository extends JpaRepository<UserAccountSmsHistory, Long>, UserAccountSmsHistoryRepositoryCustom {
}
