package com.gofield.domain.rds.domain.cart;

import com.gofield.domain.rds.domain.cart.repository.CartOptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartOptionOptionRepository extends JpaRepository<CartOption, Long>, CartOptionRepositoryCustom {
}
