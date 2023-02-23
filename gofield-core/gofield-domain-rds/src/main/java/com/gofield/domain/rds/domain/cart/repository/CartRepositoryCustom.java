package com.gofield.domain.rds.domain.cart.repository;


import com.gofield.domain.rds.domain.cart.Cart;
import com.gofield.domain.rds.domain.cart.projection.CartProjection;

import java.util.List;

public interface CartRepositoryCustom {
    int getCartCount(Long userId);
    Cart findByUserIdAndItemNumber(Long userId, String itemNumber);
    Cart findByCartIdAndUserId(Long cartId, Long userId);
    List<CartProjection> findAllByUserId(Long userId);
    List<Long> findAllInCartIdList(List<Long> cartIdList, Long userId);
    void deleteByCartIdList(List<Long> cartIdList);
    void deleteByItemNumber(String itemNumber);
    void deleteInItemNumber(List<String> itemNumberList);
}
