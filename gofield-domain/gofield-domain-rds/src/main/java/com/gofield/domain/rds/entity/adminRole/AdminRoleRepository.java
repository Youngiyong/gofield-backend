package com.gofield.domain.rds.entity.adminRole;

import com.gofield.domain.rds.entity.adminRole.repository.AdminRoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRoleRepository extends JpaRepository<AdminRole, Long>, AdminRoleRepositoryCustom {
}
