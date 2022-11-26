package com.gofield.domain.rds.domain.user.repository;


import com.gofield.domain.rds.domain.user.TermGroup;

public interface TermGroupRepositoryCustom {
    TermGroup findByGroupId(Long id);
}
