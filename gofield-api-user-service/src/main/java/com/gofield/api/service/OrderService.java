package com.gofield.api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.dto.enums.PaymentType;
import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.order.OrderSheet;
import com.gofield.domain.rds.domain.order.OrderSheetRepository;
import com.gofield.domain.rds.domain.order.OrderWait;
import com.gofield.domain.rds.domain.order.OrderWaitRepository;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
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
    private final OrderWaitRepository orderWaitRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final ThirdPartyService thirdPartyService;

    @Transactional(readOnly = true)
    public OrderSheetResponse getOrderSheet(String uuid) {
        User user = userService.getUser();
        userService.validateNonMember(user);
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), uuid);
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
    public CommonCodeResponse createOrderSheet(OrderRequest.OrderSheet request){
        User user = userService.getUser();

        List<Long> cartIdList = request.getItemList()
                .stream()
                .map(p -> Long.valueOf(String.valueOf(p.get("id"))))
                .collect(Collectors.toList());
        if(cartIdList.isEmpty()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 장바구니 아이디 리스트입니다.");
        }
        List<Long> resultCartIdList = cartRepository.findAllInCartIdList(cartIdList, user.getId());
        if(request.getItemList().size()!=resultCartIdList.size()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 장바구니 아이디가 있습니다.");
        }

        OrderSheet orderSheet = OrderSheet.newInstance(user.getId(), new Gson().toJson(request), request.getTotalPrice());
        orderSheetRepository.save(orderSheet);
        return CommonCodeResponse.of(orderSheet.getUuid());
    }

    /*
        ToDo: 주문명 수정
     */
    private String makeOrderName(String sheet){
        return "임시 주문 코드 " + RandomUtils.makeRandomCode(6);
    }

    @Transactional
    public OrderWaitResponse createOrderWait(OrderRequest.Order request){
        User user = userService.getUser();
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), request.getUuid());
        if(orderSheet==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "주문시트가 존재하지 않습니다.");
        }
        String cardCompany = request.getPaymentType().equals(PaymentType.CARD) ? request.getCompanyCode() : null;
        String easyPay = request.getPaymentType().equals(PaymentType.EASYPAY) ? request.getCompanyCode() : null;
        TossPaymentRequest.Payment externalRequest = TossPaymentRequest.Payment.of(orderSheet.getTotalPrice(), Constants.METHOD, RandomUtils.makeRandomCode(32), makeOrderName(orderSheet.getSheet()), cardCompany, easyPay, thirdPartyService.getTossPaymentSuccessUrl(), thirdPartyService.getTossPaymentFailUrl());
        TossPaymentResponse response = thirdPartyService.getPaymentReadyInfo(externalRequest);
        OrderWait orderWait = OrderWait.newInstance(user.getId(), RandomUtils.makeRandomNumberCode(64), new Gson().toJson(response), request.getEnvironment());
        orderWaitRepository.save(orderWait);
        return OrderWaitResponse.of(response.getCheckout().getUrl());
    }


    @Transactional
    public String createOrder(String oid){
        OrderWait orderWait = orderWaitRepository.findByOid(oid);
        if(orderWait==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 주문 번호입니다.", oid));
        }


        return null;
    }


}
