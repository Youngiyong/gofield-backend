package com.gofield.domain.rds.domain.cart.repository;


import com.gofield.domain.rds.domain.cart.Cart;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;

import java.util.List;

public interface CartRepositoryCustom {
    int getCartCount(Long userId);
    Cart findByUserIdAndItemNumber(Long userId, String itemNumber);

    List<CartProjection> findAllByUserId(Long userId);
}
