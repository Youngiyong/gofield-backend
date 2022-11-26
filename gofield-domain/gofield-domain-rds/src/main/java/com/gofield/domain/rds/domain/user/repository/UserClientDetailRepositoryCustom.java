package com.gofield.domain.rds.domain.user.repository;

import com.gofield.domain.rds.domain.user.UserClientDetail;

public interface UserClientDetailRepositoryCustom {
    UserClientDetail findByClientId(String clientId);
    UserClientDetail findByClientId(Long id);

}
