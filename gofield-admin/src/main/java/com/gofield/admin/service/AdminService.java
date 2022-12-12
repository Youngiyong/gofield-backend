package com.gofield.admin.service;

import com.gofield.admin.dto.request.AdminRequest;
import com.gofield.admin.dto.AdminListDto;
import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.response.projection.AdminInfoProjectionResponse;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.AdminRepository;
import com.gofield.domain.rds.domain.admin.AdminRole;
import com.gofield.domain.rds.domain.admin.AdminRoleRepository;
import com.gofield.domain.rds.domain.admin.EAdminRole;
import com.gofield.domain.rds.domain.admin.projection.AdminInfoProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final AdminRoleRepository adminRoleRepository;

    @Transactional(readOnly = true)
    public AdminListDto getAdminList(String name, Pageable pageable) {
        Page<AdminInfoProjection> page = adminRepository.findAllAdminInfoList(name, pageable);
        List<AdminInfoProjectionResponse> projectionResponse = AdminInfoProjectionResponse.of(page.getContent());
        return AdminListDto.of(projectionResponse, page);
    }

    @Transactional
    public void updateAdmin(AdminDto adminDto){
        Admin admin = adminRepository.findByAdminId(adminDto.getId());
        admin.update(adminDto.getUsername(), adminDto.getPassword()==null ? null : passwordEncoder.encode(adminDto.getPassword()), adminDto.getName(), adminDto.getRole());
    }

    @Transactional(readOnly = true)
    public AdminDto getAdmin(Long id){
        return AdminDto.of(adminRepository.findByAdminId(id));
    }

    @Transactional
    public void save(AdminDto adminDto){
        AdminRole adminRole = adminRoleRepository.findByRole(adminDto.getRole());
        Admin admin = adminRepository.findByUsername(adminDto.getUsername());
        if(admin!=null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 가입되어 있는 관리자입니다.");
        }
        admin = Admin.newInstance(adminDto.getName(), adminDto.getUsername(), passwordEncoder.encode(adminDto.getPassword()), adminRole);
        adminRepository.save(admin);
    }

    @Transactional
    public void delete(Long id){
        Admin admin = adminRepository.findByAdminId(id);
        adminRepository.delete(admin);
    }



}