package com.gofield.admin.controller;

import com.gofield.domain.rds.domain.admin.AdminRepository;
import com.gofield.domain.rds.domain.admin.AdminRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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
