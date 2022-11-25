package com.gofield.domain.rds.entity.orderShippingAddress;

import com.gofield.domain.rds.entity.orderShippingAddress.repository.OrderShippingAddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingAddressRepository extends JpaRepository<OrderShippingAddress, Long>, OrderShippingAddressRepositoryCustom {
}
