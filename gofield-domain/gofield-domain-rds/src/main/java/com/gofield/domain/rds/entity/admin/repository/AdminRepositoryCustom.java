package com.gofield.domain.rds.entity.admin.repository;

import com.gofield.domain.rds.entity.admin.Admin;

public interface AdminRepositoryCustom {

    Admin loadByUsername(String username);
}
