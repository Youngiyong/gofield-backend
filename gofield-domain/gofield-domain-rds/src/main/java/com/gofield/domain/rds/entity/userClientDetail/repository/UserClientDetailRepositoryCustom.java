package com.gofield.domain.rds.entity.userClientDetail.repository;

import com.gofield.domain.rds.entity.userClientDetail.UserClientDetail;

public interface UserClientDetailRepositoryCustom {
    UserClientDetail findByClientId(String clientId);
    UserClientDetail findByClientId(Long id);

}
