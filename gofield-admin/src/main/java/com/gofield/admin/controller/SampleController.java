package com.gofield.admin.controller;

import com.gofield.domain.rds.entity.admin.Admin;
import com.gofield.domain.rds.entity.admin.AdminRepository;
import com.gofield.domain.rds.entity.adminRole.AdminRole;
import com.gofield.domain.rds.entity.adminRole.AdminRoleRepository;
import com.gofield.domain.rds.enums.admin.EAdminRole;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final AdminRepository adminRepository;
    private final AdminRoleRepository adminRoleRepository;

//    @PostConstruct
//    @Transactional
//    public void save_admin_test(){
//        AdminRole adminRole = adminRoleRepository.findByRole(EAdminRole.ROLE_OPERATOR);
//        for(int i=0; i<155; i++){
//            Admin admin = Admin.newInstance("테스트"+i, "테스트"+i, "test", "01012345679",adminRole );
//            adminRepository.save(admin);
//        }
//    }
}
