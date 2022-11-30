package com.gofield.domain.rds.domain.cart;

import com.gofield.domain.rds.domain.cart.repository.CartTempRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartTempRepository extends JpaRepository<CartTemp, Long>, CartTempRepositoryCustom {
}
