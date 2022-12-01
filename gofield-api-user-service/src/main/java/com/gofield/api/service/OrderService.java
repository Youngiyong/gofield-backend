package com.gofield.api.service;


import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.order.OrderSheetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final OrderSheetRepository orderSheetRepository;




}
