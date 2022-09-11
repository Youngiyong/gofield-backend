package com.gofield.domain.rds.entity.userAccount;

import com.gofield.domain.rds.entity.userAccount.repository.UserAccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, UserAccountRepositoryCustom {
}
