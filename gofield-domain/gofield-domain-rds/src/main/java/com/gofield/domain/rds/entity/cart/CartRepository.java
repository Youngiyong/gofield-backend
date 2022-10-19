package com.gofield.domain.rds.entity.cart;

import com.gofield.domain.rds.entity.cart.repository.CartRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
}
