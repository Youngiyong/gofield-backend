package com.gofield.domain.rds.domain.faq.repository;

import com.gofield.domain.rds.domain.faq.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FaqRepositoryCustom {

    List<Faq> findAllOrderBySort();
    Page<Faq> findAllPaging(Pageable pageable, Boolean isUse);
    Faq findByNoticeId(Long noticeId);
}
