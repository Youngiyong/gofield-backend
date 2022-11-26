package com.gofield.admin.service.impl;

import com.gofield.domain.rds.domain.admin.Admin;
import com.gofield.domain.rds.domain.admin.AdminRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin =  adminRepository.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(admin.getAdminRole().getRole().name()));
        return new User(admin.getName(), admin.getPassword(), authorities);
    }
}
