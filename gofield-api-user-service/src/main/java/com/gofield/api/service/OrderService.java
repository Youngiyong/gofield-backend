package com.gofield.api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.dto.res.CodeResponse;
import com.gofield.api.dto.res.CommonCodeResponse;
import com.gofield.api.dto.res.OrderSheetResponse;
import com.gofield.api.dto.res.UserAddressResponse;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.order.OrderSheet;
import com.gofield.domain.rds.domain.order.OrderSheetRepository;
import com.gofield.domain.rds.domain.user.User;
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

    private final CommonService commonService;
    private final CartRepository cartRepository;
    private final OrderSheetRepository orderSheetRepository;


    @Transactional(readOnly = true)
    public OrderSheetResponse getOrderSheetTemp(String code) {
        User user = userService.getUser();
        userService.validateNonMember(user);
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), code);
        if (orderSheet == null) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "장바구니 임시 정보가 존재하지 않습니다.");
        }
        try {
            List<Map<String, Object>> result = new ObjectMapper().readValue(orderSheet.getSheet(), new TypeReference<List<Map<String, Object>>>() {});
            UserAddressResponse userAddressResponse = UserAddressResponse.of(userService.getUserMainAddress(user.getId()));
            List<CodeResponse> codeResponseList = commonService.getCodeList(ECodeGroup.SHIPPING_COMMENT);
            return OrderSheetResponse.of(result, userAddressResponse, codeResponseList);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }


    @Transactional
    public CommonCodeResponse createOrderSheet(List<Map<String,Object>> request){
        User user = userService.getUser();

        List<Long> cartIdList = request
                .stream()
                .map(p -> Long.valueOf(String.valueOf(p.get("id"))))
                .collect(Collectors.toList());
        if(cartIdList.isEmpty()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 장바구니 아이디 리스트입니다.");
        }
        List<Long> resultCartIdList = cartRepository.findAllInCartIdList(cartIdList, user.getId());
        if(request.size()!=resultCartIdList.size()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 장바구니 아이디가 있습니다.");
        }

        OrderSheet orderSheet = OrderSheet.newInstance(user.getId(), new Gson().toJson(request));
        orderSheetRepository.save(orderSheet);
        return CommonCodeResponse.of(orderSheet.getUuid());
    }

}
