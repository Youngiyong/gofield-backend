package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserAccountSmsHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountSmsHistoryRepository extends JpaRepository<UserAccountSmsHistory, Long>, UserAccountSmsHistoryRepositoryCustom {
}
