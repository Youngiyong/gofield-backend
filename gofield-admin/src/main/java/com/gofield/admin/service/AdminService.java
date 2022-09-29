package com.gofield.admin.service;

import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import com.gofield.domain.rds.entity.adminRole.AdminRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin =  adminRepository.loadByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        AdminRole adminRole = admin.getAdminRole();
        authorities.add(new SimpleGrantedAuthority(adminRole.getRole().name()));
        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }

}
