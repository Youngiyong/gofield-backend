package com.gofield.domain.rds.domain.code.repository;


import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.ECodeGroup;

import java.util.List;

public interface CodeRepositoryCustom {
    List<Code> findAllByGroupByIsHide(ECodeGroup group, Boolean isVisible);
    Code findByCode(String code);
}
