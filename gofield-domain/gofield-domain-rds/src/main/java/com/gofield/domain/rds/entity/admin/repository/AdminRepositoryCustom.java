package com.gofield.domain.rds.entity.admin.repository;

import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.projections.AdminInfoProjection;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminRepositoryCustom {

    Admin findByUsername(String username);
    Admin findByAdminId(Long adminId);
    List<AdminInfoProjection> findAllAdminInfoList(Pageable pageable);
    AdminInfoProjection findAdminInfoProjectionById(Long id);
    List<Admin> findAllList(Pageable pageable);
}
