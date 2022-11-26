package com.gofield.api.service;

import com.gofield.api.dto.res.CountResponse;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final UserService userService;
    private final CartRepository cartRepository;

    @Transactional(readOnly = true)
    public CountResponse getCartCount(){
        return CountResponse.of(cartRepository.getCartCount(userService.getUserDecryptUuid()));
    }

    @Transactional
    public void insertCart(Long itemId){
        User user = userService.getUser();

    }
}
