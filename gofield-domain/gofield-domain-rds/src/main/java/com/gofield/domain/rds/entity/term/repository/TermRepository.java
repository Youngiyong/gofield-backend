package com.gofield.domain.rds.entity.term.repository;

import com.gofield.domain.rds.entity.term.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermRepository extends JpaRepository<Term, Long>, TermRepositoryCustom {
}
