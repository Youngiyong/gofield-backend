package com.gofield.domain.rds.entity.userHasTerm;

import com.gofield.domain.rds.entity.userHasTerm.repository.UserHasTermRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasTermRepository extends JpaRepository<UserHasTerm, Long>, UserHasTermRepositoryCustom {
}
