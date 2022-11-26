package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.TermRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TermRepository extends JpaRepository<Term, Long>, TermRepositoryCustom {
}
