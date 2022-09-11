package com.gofield.domain.rds.entity.term;

import com.gofield.domain.rds.entity.term.repository.TermRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TermRepository extends JpaRepository<Term, Long>, TermRepositoryCustom {
}
