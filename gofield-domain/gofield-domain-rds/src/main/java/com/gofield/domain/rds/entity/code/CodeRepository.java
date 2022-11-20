package com.gofield.domain.rds.entity.code;

import com.gofield.domain.rds.entity.code.repository.CodeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long>, CodeRepositoryCustom {
}
