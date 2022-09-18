package com.gofield.domain.rds.entity.termGroup.repository;


import com.gofield.domain.rds.entity.termGroup.TermGroup;

public interface TermGroupRepositoryCustom {
    TermGroup findByGroupId(Long id);
}
