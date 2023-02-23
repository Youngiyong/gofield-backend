package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserTermRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermRepository extends JpaRepository<UserTerm, Long>, UserTermRepositoryCustom {
}
