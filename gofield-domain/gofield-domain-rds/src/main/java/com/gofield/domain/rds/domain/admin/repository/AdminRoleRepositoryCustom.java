package com.gofield.domain.rds.domain.admin.repository;


import com.gofield.domain.rds.domain.admin.AdminRole;
import com.gofield.domain.rds.domain.admin.EAdminRole;

public interface AdminRoleRepositoryCustom {
    AdminRole findByRole(EAdminRole role);
}
