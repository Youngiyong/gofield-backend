package com.gofield.domain.rds.entity.userAccount.repository;

import com.gofield.domain.rds.entity.userAccount.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>, UserAccountRepositoryCustom {
}
