package com.gofield.api.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.dto.enums.PaymentType;
import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.*;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.exception.*;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.code.Code;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemBundleReviewScoreProjection;
import com.gofield.domain.rds.domain.item.projection.ItemOrderSheetProjection;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentCancelResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CodeRepository codeRepository;
    private final ItemRepository itemRepository;
    private final OrderWaitRepository orderWaitRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;

    private final OrderItemRepository orderItemRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingAddressRepository orderShippingAddressRepository;
    private final ItemBundleReviewRepository itemBundleReviewRepository;

    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final UserService userService;
    private final ThirdPartyService thirdPartyService;
    private final S3FileStorageClient s3FileStorageClient;


    public String makeOrderNumber(){
        return String.valueOf(Calendar.getInstance(Locale.KOREA).getTimeInMillis());
    }
    public String makeCarrierUrl(String carrierId, String trackId){
        return String.format(Constants.TRACKER_DELIVERY_URL, carrierId, trackId);
    }

    @Transactional(readOnly = true)
    public Order getOrder(String orderNumber){
        User user = userService.getUserNotNonUser();
        return orderRepository.findByOrderNumberAndUserId(orderNumber, user.getId());
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
        OrderSheetContentResponse result = ApiUtil.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>() {});
        UserAddressResponse userAddressResponse = UserAddressResponse.of(userService.getUserMainAddress(user.getId()));
        return OrderSheetResponse.of(result.getOrderSheetList(), userAddressResponse);
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
    public OrderListResponse getOrderList(Pageable pageable){
        User user = userService.getUserNotNonUser();
        List<Order> orderList = orderRepository.findAllByUserIdAndNotStatusDelete(user.getId(), pageable);
        return OrderListResponse.of(OrderResponse.of(orderList));
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
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 판매 상품 갯수가 초과된 상품입니다.", sheetItem.getItemNumber()));
            }
            int price = itemStock.getIsOption() ? itemStock.getOptionPrice() : itemStock.getPrice();
            int deliveryPrice = itemStock.getCharge();
            if(itemStock.getCondition()<=price*sheetItem.getQty()){
                deliveryPrice = 0;
            }
            totalPrice += price*sheetItem.getQty();
            totalDelivery += deliveryPrice;
            ItemOrderSheetResponse orderSheet = ItemOrderSheetResponse.of(itemStock.getId(), itemStock.getSellerId(), itemStock.getBundleId(), itemStock.getBrandName(), itemStock.getName(), itemStock.getOptionName(), itemStock.getThumbnail(), itemStock.getItemNumber(), price, sheetItem.getQty(), deliveryPrice, itemStock.getOptionId(),itemStock.getIsOption(), itemStock.getOptionType(), itemStock.getChargeType(), itemStock.getCharge(), itemStock.getCondition(), itemStock.getFeeJeju(), itemStock.getFeeJejuBesides());
            result.add(orderSheet);
        }
        if(request.getTotalPrice()!=totalPrice){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "총 금액이 맞지 않습니다.");
        }else if(request.getTotalDelivery()!=totalDelivery){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "총 배 금액이 맞지 않습니다.");
        }
        ItemOrderSheetListResponse list = ItemOrderSheetListResponse.of(totalPrice, totalDelivery, result);
        List<Long> cartIdList = request.getIsCart() ? request.getItems().stream().map(p -> p.getCartId()).collect(Collectors.toList()) : null;
        OrderSheetContentResponse contentResponse = OrderSheetContentResponse.of(makeOrderName(result), list, cartIdList);
        OrderSheet orderSheet = OrderSheet.newInstance(user.getId(), ApiUtil.toJsonStr(contentResponse), totalPrice+totalDelivery);
        orderSheetRepository.save(orderSheet);
        return CommonCodeResponse.of(orderSheet.getUuid());
    }


    private String makeOrderName(List<ItemOrderSheetResponse> list){
        if(list.size()>1){
           return String.format("%s 외 %s건", list.get(0).getName(), list.size()-1);
        } else {
            return list.get(0).getName();
        }
    }

    @Transactional
    public OrderWaitResponse createOrderWait(OrderRequest.OrderPay request){
        User user = userService.getUserNotNonUser();
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), request.getUuid());
        if(orderSheet==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "주문시트가 존재하지 않습니다.");
        }

        OrderSheetContentResponse orderSheetContent = null;
        orderSheetContent =  ApiUtil.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>(){});
        String cardCompany = request.getPaymentType().equals(PaymentType.CARD) ? request.getCompanyCode() : null;
        String easyPay = request.getPaymentType().equals(PaymentType.EASYPAY) ? request.getCompanyCode() : null;
        TossPaymentRequest.Payment externalRequest = TossPaymentRequest.Payment.of(orderSheet.getTotalPrice(), Constants.METHOD, makeOrderNumber(), orderSheetContent.getOrderName(), cardCompany, easyPay, thirdPartyService.getTossPaymentSuccessUrl(), thirdPartyService.getTossPaymentFailUrl());
        TossPaymentResponse response = thirdPartyService.getPaymentReadyInfo(externalRequest);
        OrderWait orderWait = OrderWait.newInstance(user.getId(), externalRequest.getOrderId(), orderSheet.getUuid(),  new Gson().toJson(response), new Gson().toJson(request.getShippingAddress()), request.getPaymentType(), request.getEnvironment());
        orderWaitRepository.save(orderWait);
        return OrderWaitResponse.of(response.getCheckout().getUrl());
    }

    @Transactional
    public void cancelOrderShipping(String orderNumber, String shippingNumber, OrderRequest.OrderCancel request){

    }

    public void cancelPayment(String orderNumber)  {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        TossPaymentRequest.PaymentCancel request = TossPaymentRequest.PaymentCancel.builder()
                .cancelAmount(order.getTotalPrice()+order.getTotalDelivery())
                .cancelReason("취소 사유입니다.")
                .build();

        TossPaymentCancelResponse response = thirdPartyService.cancelPayment(order.getPaymentKey(), request);
        System.out.println("..");
    }


    @Transactional
    public void deleteOrderShipping(String orderNumber, String shippingNumber){
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumber(shippingNumber, orderNumber);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 상품 확인중이거나 완료된 배송은 구매 내역 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 준비중인 배송은 구매 내역 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 아직 배송중이어서 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 이미 삭제처리가 되어 있는 배송 번호입니다.", shippingNumber));
        }

        orderShipping.updateDelete();
    }

    @Transactional
    public void completeOrderShipping(String orderNumber, String shippingNumber){
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumber(shippingNumber, orderNumber);

        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 상품 확인중이거나 완료된 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 취소 처리된 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 준비중인 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 삭제 처리된 배송은 구매 확정이 불가합니다.", shippingNumber));
        }

        orderShipping.updateComplete();
    }


    @Transactional(readOnly = true)
    public OrderItemReviewListResponse getOrderItemReviewList(Boolean isReview, Pageable pageable){
        User user = userService.getUserNotNonUser();
        List<OrderItemProjection> result = orderItemRepository.findAllByUserId(user.getId(), isReview, pageable);
        List<OrderItemReviewResponse> response = OrderItemReviewResponse.of(result);
        return OrderItemReviewListResponse.of(response);
    }

    @Transactional
    public void reviewOrderShippingItem(Long orderItemId, OrderRequest.OrderReview request, List<MultipartFile> files){
       /*
       ToDo: 주문에 해당하는 사용자 검증필요 api 전부
        */
        User user = userService.getUserNotNonUser();
        OrderItem orderItem = orderItemRepository.findByOrderItemId(orderItemId);
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("%s 존재하지 않는 주문 상품 아이디입니다.", orderItemId));
        }
        Order order = orderRepository.findByOrderNumberAndUserId(orderItem.getOrderNumber(), user.getId());
        if(order==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "존재하지 않는 주문정보입니다.");
        }
        if(orderItem.getIsReview()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 등록된 상품 리뷰가 있습니다.");
        }
        if(!orderItem.getOrderShipping().isShippingReview()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "인계 완료된 상품이 아니거나 구매 확정된 상품이 아닙니다.");
        }
        List<String> imageList = new ArrayList<>();
        if(files!=null){
            for(MultipartFile file: files){
                String image = s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_IMAGE);
                imageList.add(image);
            }
        }
        String optionName = null;
        if(orderItem.getOrderItemOption()!=null){
            List<String> optionList = ApiUtil.toObject(orderItem.getOrderItemOption().getName(), List.class);
            optionName = optionList.stream().collect(Collectors.joining(" ")) + " 구매";
        }
        ItemBundleReview itemBundleReview = ItemBundleReview.newInstance(orderItem.getItem().getBundle(), user.getId(), orderItem.getName(), user.getNickName(), optionName, request.getWeight(), request.getHeight(), request.getReviewScore(), request.getContent());
        for(String image: imageList){
            ItemBundleReviewImage itemBundleReviewImage = ItemBundleReviewImage.newInstance(itemBundleReview, image);
            itemBundleReview.addImage(itemBundleReviewImage);
        }
        itemBundleReviewRepository.save(itemBundleReview);
        List<ItemBundleReviewScoreProjection> reviewList = itemBundleReviewRepository.findAllByBundleId(orderItem.getItem().getBundle().getId());
        if(!reviewList.isEmpty() && reviewList.size()>0){
            ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(orderItem.getItem().getBundle().getId());
            int reviewCount = reviewList.size();
            Double reviewScore = reviewList.stream().mapToDouble(i -> i.getReviewScore()).sum();
            itemBundleAggregation.updateReviewScore(reviewCount, reviewScore/reviewCount);
        }
        orderItem.updateReview();
    }
}
