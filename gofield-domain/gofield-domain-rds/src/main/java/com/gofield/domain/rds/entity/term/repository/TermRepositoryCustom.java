package com.gofield.domain.rds.entity.term.repository;

import com.gofield.domain.rds.entity.term.Term;

import java.util.List;

public interface TermRepositoryCustom {
    List<Term> findAllByInId(List<Long> idList);

}
