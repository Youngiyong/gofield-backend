package com.gofield.domain.rds.entity.userAddress;

import com.gofield.domain.rds.entity.userAddress.repository.UserAddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>, UserAddressRepositoryCustom {

}
