package com.gofield.domain.rds.domain.faq.repository;

import com.gofield.domain.rds.domain.faq.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqRepositoryCustom {
    Page<Faq> findAllPaging(Pageable pageable, Boolean isUse);
    Faq findByFaqId(Long faqId);
}
