package com.gofield.domain.rds.domain.admin;

import com.gofield.domain.rds.domain.admin.repository.AdminMenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminMenuRepository extends JpaRepository<AdminMenu, Long>, AdminMenuRepositoryCustom {
}
