package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.OrderShippingAddressRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderShippingAddressRepository extends JpaRepository<OrderShippingAddress, Long>, OrderShippingAddressRepositoryCustom {
}
