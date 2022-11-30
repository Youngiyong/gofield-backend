package com.gofield.api.service;


import com.gofield.api.dto.req.CartRequest;
import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.cart.CartTemp;
import com.gofield.domain.rds.domain.cart.CartTempRepository;
import com.gofield.domain.rds.domain.item.ItemStockRepository;
import com.gofield.domain.rds.domain.user.User;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;

    private final CartRepository cartRepository;
    private final CartTempRepository cartTempRepository;
    private final ItemStockRepository itemStockRepository;

    @Transactional
    public CommonCodeResponse insertCartTemp(List<CartRequest.CartTemp> request){
        User user = userService.getUser();

        List<Long> cartIdList = request
                .stream()
                .map(p -> p.getCartId())
                .collect(Collectors.toList());
        if(cartIdList.isEmpty()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 장바구니 아이디 리스트입니다.");
        }
        List<Long> resultCartIdList = cartRepository.findAllInCartIdList(cartIdList, user.getId());
        if(request.size()!=resultCartIdList.size()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 장바구니 아이디가 있습니다.");
        }

        CartTemp cartTemp = CartTemp.newInstance(user.getId(), new Gson().toJson(request));
        cartTempRepository.save(cartTemp);
        return CommonCodeResponse.of(cartTemp.getUuid());
    }
}
