package com.gofield.admin.service;

import com.gofield.admin.dto.AdminDto;
import com.gofield.admin.dto.Constants;
import com.gofield.admin.dto.request.LoginRequest;
import com.gofield.admin.dto.response.AdminResponse;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.EncryptUtils;
import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public HttpHeaders login(LoginRequest request, HttpServletRequest servletRequest) {

        Admin admin = adminRepository.findByUsername(request.getUsername());
        String context = servletRequest.getContextPath();
        URI redirectUri = null;
        try {
            if(admin==null || (!EncryptUtils.sha256Encrypt(request.getPassword()).equals(admin.getPassword()))) {
                redirectUri = new URI(context + Constants.LOGIN_ERROR_PAGE);
            } else {
                AdminDto adminDto = AdminDto.of(admin.getId(), admin.getName(), admin.getUuid());
                servletRequest.getSession().setAttribute("sAdmin", adminDto);
                redirectUri = new URI(context + Constants.LOGIN_AFTER_PAGE);
            }
        } catch (URISyntaxException e) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.RETRY, "retry");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return httpHeaders;
    }

}