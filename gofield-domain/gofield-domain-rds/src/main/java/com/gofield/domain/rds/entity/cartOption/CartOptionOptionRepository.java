package com.gofield.domain.rds.entity.cartOption;

import com.gofield.domain.rds.entity.cartOption.repository.CartOptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartOptionOptionRepository extends JpaRepository<CartOption, Long>, CartOptionRepositoryCustom {
}
