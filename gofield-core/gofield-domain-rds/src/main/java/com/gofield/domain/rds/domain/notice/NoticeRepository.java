package com.gofield.domain.rds.domain.notice;

import com.gofield.domain.rds.domain.notice.repository.NoticeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeRepositoryCustom {
}
