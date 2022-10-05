package com.gofield.domain.rds.entity.adminRole.repository;


import com.gofield.domain.rds.entity.adminRole.AdminRole;
import com.gofield.domain.rds.enums.admin.EAdminRole;

public interface AdminRoleRepositoryCustom {
    AdminRole findByRole(EAdminRole role);
}
