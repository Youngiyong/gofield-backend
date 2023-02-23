package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.order.repository.PurchaseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseRepositoryCustom {
}
