package com.gofield.admin.service;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.request.AdminRequest;
import com.gofield.admin.dto.response.AdminInfoResponse;
import com.gofield.admin.dto.response.AdminResponse;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import com.gofield.domain.rds.entity.adminRole.AdminRole;
import com.gofield.domain.rds.entity.adminRole.AdminRoleRepository;
import com.gofield.domain.rds.enums.admin.EAdminRole;
import com.gofield.domain.rds.projections.AdminInfoProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminRoleRepository adminRoleRepository;

    @Transactional(readOnly = true)
    public List<AdminResponse> findAdminList() {
//        List<AdminInfoProjection> adminList = adminRepository.findAllAdminInfoList(null);
        return AdminResponse.ofList(null);
    }

    @Transactional
    public AdminInfoResponse findAdminById(Long id){
        AdminInfoProjection adminInfoProjection = adminRepository.findAdminInfoProjectionById(id);
        return AdminInfoResponse.of(adminInfoProjection);
    }


    @Transactional
    public void save(AdminRequest.Create request){
        Admin admin = null;
        if(request.getId()==null){
            AdminRole adminRole = adminRoleRepository.findByRole(EAdminRole.ROLE_ADMIN);
            admin = Admin.newInstance(request.getName(), request.getUsername(), EncryptUtils.sha256Encrypt(request.getPassword()), request.getTel(), adminRole);
            adminRepository.save(admin);
        } else {
            admin = adminRepository.findByAdminId(request.getId());
            admin.update(EncryptUtils.sha256Encrypt(request.getPassword()), request.getName(), request.getTel());
        }
    }

    @Transactional
    public void delete(Long id){
        Admin admin = adminRepository.findByAdminId(id);
        adminRepository.delete(admin);
    }



}