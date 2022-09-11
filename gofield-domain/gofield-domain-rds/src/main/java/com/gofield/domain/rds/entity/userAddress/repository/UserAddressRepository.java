package com.gofield.domain.rds.entity.userAddress.repository;

import com.gofield.domain.rds.entity.userAddress.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long>, UserAddressRepositoryCustom {

}
