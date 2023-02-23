package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.Term;
import com.gofield.domain.rds.domain.user.ETermFlag;

import java.util.List;

public interface TermRepositoryCustom {
    List<Term> findAllByInId(List<Long> idList);
    Term findByType(ETermFlag type);

}
