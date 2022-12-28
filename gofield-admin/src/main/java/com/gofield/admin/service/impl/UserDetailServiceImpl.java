package com.gofield.admin.service.impl;

import com.gofield.admin.util.AdminUtil;
import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.AdminAccessLog;
import com.gofield.domain.rds.domain.admin.AdminAccessLogRepository;
import com.gofield.domain.rds.domain.admin.AdminRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final AdminAccessLogRepository adminAccessLogRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin =  adminRepository.findByUsername(username);
        if(admin!=null){
            HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String ipAddress = AdminUtil.getIpAddress(servletRequest);
            String userAgent = servletRequest.getHeader("User-Agent");
            AdminAccessLog adminAccessLog = AdminAccessLog.newInstance(admin, userAgent, ipAddress);
            adminAccessLogRepository.save(adminAccessLog);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(admin.getAdminRole().getRole().name()));
        return new User(admin.getName(), admin.getPassword(), authorities);
    }
}

