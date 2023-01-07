package com.gofield.domain.rds.domain.faq;

import com.gofield.domain.rds.domain.faq.repository.FaqRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long>, FaqRepositoryCustom {
}
