package com.gofield.domain.rds.entity.termGroup.repository;

import com.gofield.domain.rds.entity.termGroup.TermGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermGroupRepository extends JpaRepository<TermGroup, Long>, TermGroupRepositoryCustom {
}
