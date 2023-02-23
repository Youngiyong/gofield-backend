package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserAccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, UserAccountRepositoryCustom {
}
