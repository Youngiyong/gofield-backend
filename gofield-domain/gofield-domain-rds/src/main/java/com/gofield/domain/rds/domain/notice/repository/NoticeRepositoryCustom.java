package com.gofield.domain.rds.domain.notice.repository;

import com.gofield.domain.rds.domain.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<Notice> findAllOrderBySort();
    Page<Notice> findAllPaging(Pageable pageable);
    Notice findByNoticeId(Long noticeId);
}
