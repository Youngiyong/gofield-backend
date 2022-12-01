package com.gofield.api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.dto.req.CartRequest;
import com.gofield.api.dto.res.CodeResponse;
import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.dto.res.OrderTempResponse;
import com.gofield.api.dto.res.UserAddressResponse;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.cart.CartTemp;
import com.gofield.domain.rds.domain.cart.CartTempRepository;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.item.ItemStockRepository;
import com.gofield.domain.rds.domain.order.OrderTemp;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.user.UserAddress;
import com.gofield.domain.rds.domain.user.UserAddressRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final CartRepository cartRepository;
    private final CartTempRepository cartTempRepository;




}
