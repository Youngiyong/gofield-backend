package com.gofield.domain.rds.domain.cart;

import com.gofield.domain.rds.domain.cart.repository.CartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
}
