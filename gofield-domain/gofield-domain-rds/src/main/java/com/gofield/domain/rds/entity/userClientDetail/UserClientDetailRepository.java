package com.gofield.domain.rds.entity.userClientDetail;

import com.gofield.domain.rds.entity.userClientDetail.repository.UserClientDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserClientDetailRepository extends JpaRepository<UserClientDetail, Long>, UserClientDetailRepositoryCustom {
}
