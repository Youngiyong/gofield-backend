package com.gofield.domain.rds.domain.user;

import com.gofield.domain.rds.domain.user.repository.UserAddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long>, UserAddressRepositoryCustom {

}
