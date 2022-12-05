package com.gofield.api.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.api.dto.enums.PaymentType;
import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.*;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.exception.NotFoundException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemOrderSheetProjection;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {


    private final CodeRepository codeRepository;
    private final ItemRepository itemRepository;
    private final OrderWaitRepository orderWaitRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;

    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingAddressRepository orderShippingAddressRepository;
    private final UserService userService;
    private final ThirdPartyService thirdPartyService;

    public String makeOrderNumber(){
        return String.valueOf(Calendar.getInstance(Locale.KOREA).getTimeInMillis());
    }
    public String makeCarrierUrl(String carrierId, String trackId){
        return String.format(Constants.TRACKER_DELIVERY_URL, carrierId, trackId);
    }

    @Transactional(readOnly = true)
    public NextUrlResponse getOrderTrackerDeliveryUrl(String carrierId, String trackId){
        Code code = codeRepository.findByCode(carrierId);
        if(code==null) throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> carrierId는 존재하지 않는 코드입니다.", carrierId));
        return NextUrlResponse.of(makeCarrierUrl(carrierId, trackId));
    }


    @Transactional(readOnly = true)
    public OrderSheetResponse getOrderSheet(String uuid) {
        User user = userService.getUserNotNonUser();
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), uuid);
        if (orderSheet == null) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "장바구니 임시 정보가 존재하지 않습니다.");
        }
        try {
            ItemOrderSheetListResponse result = new ObjectMapper().readValue(orderSheet.getSheet(), new TypeReference<ItemOrderSheetListResponse>() {});
            UserAddressResponse userAddressResponse = UserAddressResponse.of(userService.getUserMainAddress(user.getId()));
            return OrderSheetResponse.of(result, userAddressResponse);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(String orderNumber){
        User user = userService.getUserNotNonUser();
        Order order = orderRepository.findByOrderNumberAndUserIdAndNotStatusDelete(user.getId(), orderNumber);
        if(order==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 주문번호입니다.", orderNumber));
        }
        return OrderDetailResponse.of(order, OrderShippingResponse.of(order.getOrderShippings()));
    }

    @Transactional(readOnly = true)
    public OrderShippingDetailResponse getOrderShipping(String orderNumber, String shippingNumber){
        User user = userService.getUserNotNonUser();
        OrderShippingAddress orderShippingAddress = orderShippingAddressRepository.findByOrderNumber(orderNumber);
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumber(shippingNumber, orderNumber);
        return OrderShippingDetailResponse.of(orderShipping, orderShippingAddress);
      }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderList(Pageable pageable){
        User user = userService.getUserNotNonUser();
        List<Order> orderList = orderRepository.findAllByUserIdAndNotStatusDelete(user.getId(), pageable);
        return OrderResponse.of(orderList);
    }

    @Transactional
    public CommonCodeResponse createOrderSheet(OrderRequest.OrderSheet request){
        User user = userService.getUserNotNonUser();
        int totalPrice = 0;
        int totalDelivery = 0;
        List<ItemOrderSheetResponse> result = new ArrayList<>();
        /*
        ToDo: 배송비 정책 정해지면 배송비 반영해서 가격 계산
         */
        for(OrderRequest.OrderSheet.OrderSheetItem sheetItem: request.getItems()){
            ItemOrderSheetProjection itemStock = itemRepository.findItemOrderSheetByItemNumber(sheetItem.getItemNumber());
            if(itemStock==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 존재하지 않는 상품번호입니다.", sheetItem.getItemNumber()));
            }
            if(!itemStock.getStatus().equals(EItemStatusFlag.SALE)){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST,  String.format("<%s>는 판매중이지 않는 상품번호입니다.", sheetItem.getItemNumber()));
            }
            if(sheetItem.getQty()>itemStock.getQty()){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 판매 상품 갯수가 초가된 상품입니다.", sheetItem.getItemNumber()));
            }
            int price = itemStock.getIsOption() ? itemStock.getOptionPrice() : itemStock.getPrice();
            int deliveryPrice = itemStock.getCharge();
            if(itemStock.getCondition()<=price*sheetItem.getQty()){
                deliveryPrice = 0;
            }
            totalPrice += price*sheetItem.getQty();
            totalDelivery += deliveryPrice;
            ItemOrderSheetResponse orderSheet = ItemOrderSheetResponse.of(itemStock.getId(), itemStock.getSellerId(), itemStock.getBrandName(), itemStock.getName(), itemStock.getOptionName(), itemStock.getThumbnail(), itemStock.getItemNumber(), itemStock.getPrice(), sheetItem.getQty(), deliveryPrice, itemStock.getOptionId(),itemStock.getIsOption(), itemStock.getOptionType(), itemStock.getChargeType(), itemStock.getCharge(), itemStock.getCondition(), itemStock.getFeeJeju(), itemStock.getFeeJejuBesides());
            result.add(orderSheet);
        }
        if(request.getTotalPrice()!=totalPrice){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "총 금액이 맞지 않습니다.");
        }else if(request.getTotalDelivery()!=totalDelivery){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "총 배달 금액이 맞지 않습니다.");
        }
        ItemOrderSheetListResponse list = ItemOrderSheetListResponse.of(totalPrice, totalDelivery, result);
        try {
            OrderSheet orderSheet = OrderSheet.newInstance(user.getId(), new ObjectMapper().writeValueAsString(list), totalPrice+totalDelivery);
            orderSheetRepository.save(orderSheet);
            return CommonCodeResponse.of(orderSheet.getUuid());
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

    /*
        ToDo: 주문명 수정
     */
    private String makeOrderName(String sheet){
        return "임시 주문 이름: " + RandomUtils.makeRandomNumberCode(6);
    }

    @Transactional
    public OrderWaitResponse createOrderWait(OrderRequest.OrderPay request){
        User user = userService.getUserNotNonUser();
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), request.getUuid());
        if(orderSheet==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "주문시트가 존재하지 않습니다.");
        }
        String cardCompany = request.getPaymentType().equals(PaymentType.CARD) ? request.getCompanyCode() : null;
        String easyPay = request.getPaymentType().equals(PaymentType.EASYPAY) ? request.getCompanyCode() : null;
        TossPaymentRequest.Payment externalRequest = TossPaymentRequest.Payment.of(orderSheet.getTotalPrice(), Constants.METHOD, makeOrderNumber(), makeOrderName(orderSheet.getSheet()), cardCompany, easyPay, thirdPartyService.getTossPaymentSuccessUrl(), thirdPartyService.getTossPaymentFailUrl());
        TossPaymentResponse response = thirdPartyService.getPaymentReadyInfo(externalRequest);
        OrderWait orderWait = OrderWait.newInstance(user.getId(), externalRequest.getOrderId(), orderSheet.getUuid(),  new Gson().toJson(response), new Gson().toJson(request.getShippingAddress()), request.getPaymentType(), request.getEnvironment());
        orderWaitRepository.save(orderWait);
        return OrderWaitResponse.of(response.getCheckout().getUrl());
    }



}
