package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserClientDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClientDetailRepository extends JpaRepository<UserClientDetail, Long>, UserClientDetailRepositoryCustom {
}
