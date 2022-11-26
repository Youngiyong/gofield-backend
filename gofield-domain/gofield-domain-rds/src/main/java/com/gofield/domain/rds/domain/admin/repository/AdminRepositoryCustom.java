package com.gofield.domain.rds.domain.admin.repository;

import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRepositoryCustom {

    Admin findByUsername(String username);
    Admin findByAdminId(Long adminId);
    Page<AdminInfoProjection> findAllAdminInfoList(Pageable pageable);
    AdminInfoProjection findAdminInfoProjectionById(Long id);
    List<Admin> findAllList(Pageable pageable);
}
