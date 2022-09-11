package com.gofield.domain.rds.entity.userClientDetail.repository;

import com.gofield.domain.rds.entity.userClientDetail.UserClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClientDetailRepository extends JpaRepository<UserClientDetail, Long>, UserClientDetailRepositoryCustom {
}
