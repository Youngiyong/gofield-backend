package com.gofield.domain.rds.domain.item.repository;

import com.gofield.domain.rds.domain.seller.Seller;

public interface SellerRepositoryCustom {
    Seller findBySellerId(Long sellerId);
}
