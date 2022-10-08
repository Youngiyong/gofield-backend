package com.gofield.admin.service;

import com.gofield.admin.dto.response.DashboardResponse;
import com.gofield.domain.rds.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(){
        return DashboardResponse.of(userRepository.findUserTotalCount(), 0, 0,0);
    }

}
