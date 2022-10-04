package com.gofield.admin.service;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.response.AdminResponse;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public AdminResponse getAdminList(HttpServletRequest request, Pageable pageable) {
        AdminDto admin = (AdminDto) request.getSession().getAttribute("sAdmin");

        return null;
    }
}
