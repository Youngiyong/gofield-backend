package com.gofield.domain.rds.entity.term.repository;

import com.gofield.domain.rds.entity.term.Term;
import com.gofield.domain.rds.enums.ETermFlag;

import java.util.List;

public interface TermRepositoryCustom {
    List<Term> findAllByInId(List<Long> idList);
    Term findByType(ETermFlag type);

}
