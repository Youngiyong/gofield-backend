package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.TermGroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermGroupRepository extends JpaRepository<TermGroup, Long>, TermGroupRepositoryCustom {
}
