package com.gofield.domain.rds.entity.userHasTerm.repository;

import com.gofield.domain.rds.entity.userHasTerm.UserHasTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasTermRepository extends JpaRepository<UserHasTerm, Long>, UserHasTermRepositoryCustom {
}
