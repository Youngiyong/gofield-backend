package com.gofield.domain.rds.domain.code;

import com.gofield.domain.rds.domain.code.repository.CodeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long>, CodeRepositoryCustom {
}
