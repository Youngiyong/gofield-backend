package com.gofield.domain.rds.entity.seller;

import com.gofield.domain.rds.entity.seller.repository.SellerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long>, SellerRepositoryCustom {
}
