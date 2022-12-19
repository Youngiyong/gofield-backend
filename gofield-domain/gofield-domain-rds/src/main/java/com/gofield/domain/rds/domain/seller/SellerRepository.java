package com.gofield.domain.rds.domain.seller;

import com.gofield.domain.rds.domain.item.repository.SellerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long>, SellerRepositoryCustom {
}
