package com.gofield.domain.rds.entity.code.repository;


import com.gofield.domain.rds.entity.code.Code;
import com.gofield.domain.rds.enums.ECodeGroup;

import java.util.List;

public interface CodeRepositoryCustom {
    List<Code> findByGroup(ECodeGroup group);
}
