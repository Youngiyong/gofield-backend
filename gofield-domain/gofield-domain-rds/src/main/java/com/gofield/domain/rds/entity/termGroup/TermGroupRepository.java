package com.gofield.domain.rds.entity.termGroup;

import com.gofield.domain.rds.entity.termGroup.repository.TermGroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermGroupRepository extends JpaRepository<TermGroup, Long>, TermGroupRepositoryCustom {
}
