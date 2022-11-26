package com.gofield.domain.rds.domain.admin;

import com.gofield.domain.rds.domain.admin.repository.AdminRoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRoleRepository extends JpaRepository<AdminRole, Long>, AdminRoleRepositoryCustom {
}
