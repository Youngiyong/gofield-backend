package com.gofield.domain.rds.entity.user;

import com.gofield.domain.rds.entity.user.repository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
