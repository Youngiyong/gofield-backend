package com.gofield.domain.rds.domain.notice.repository;

import com.gofield.domain.rds.domain.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeRepositoryCustom {
    Page<Notice> findAllPaging(Pageable pageable, Boolean isVisible);
    Notice findByNoticeId(Long noticeId);
}
