package com.gofield.api.service.order;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.service.item.dto.response.ItemOrderSheetListResponse;
import com.gofield.api.service.item.dto.response.ItemOrderSheetResponse;
import com.gofield.api.service.order.dto.response.*;
import com.gofield.api.service.sns.SnsService;
import com.gofield.api.service.thirdparty.ThirdPartyService;
import com.gofield.api.service.user.UserService;
import com.gofield.api.service.user.dto.request.UserRequest;
import com.gofield.api.service.user.dto.response.UserAddressResponse;
import com.gofield.api.util.SlackUtil;
import com.gofield.common.model.SlackChannel;
import com.gofield.api.service.order.dto.request.OrderRequest;
import com.gofield.common.exception.*;
import com.gofield.common.model.Constants;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.CommonUtils;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.RandomUtils;
import com.gofield.domain.rds.domain.cart.CartRepository;
import com.gofield.domain.rds.domain.common.EGofieldService;
import com.gofield.domain.rds.domain.common.PaginationResponse;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemBundleReviewScoreProjection;
import com.gofield.domain.rds.domain.item.projection.ItemOrderSheetProjection;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentVirtualResponse;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
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
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final OrderWaitRepository orderWaitRepository;
    private final OrderRepository orderRepository;
    private final OrderShippingLogRepository orderShippingLogRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderCancelRepository orderCancelRepository;
    private final OrderCancelCommentRepository orderCancelCommentRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemOptionRepository orderItemOptionRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingAddressRepository orderShippingAddressRepository;
    private final ItemBundleReviewRepository itemBundleReviewRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final UserService userService;
    private final ThirdPartyService thirdPartyService;
    private final S3FileStorageClient s3FileStorageClient;

    private final SnsService snsService;

    private void validateOrderShippingReturnAndChangeStatus(EOrderShippingStatusFlag status){
        if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "구매 확정된 상품은 교환/반품 진행이 불가합니다.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 주문 취소가 접수된 상품입니다.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 주문 취소가 완료된 상품입니다.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 반품이 진행중인 상품입니다.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 반품이 완료된 상품입니다.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 교환 신청이 진행중인 상품입니다.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 교환이 완료된 상품입니다.");
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "교환/반품 진행이 불가한 상품입니다.");
        }
    }

    public String makeOrderCancelNumber() {
        StringBuilder cancelNumber =  new StringBuilder(String.valueOf(Calendar.getInstance(Locale.KOREA).getTimeInMillis()));
        cancelNumber.setCharAt(0, '2');
        return cancelNumber.toString();
    }
    public String makeOrderNumber(){
        return String.valueOf(Calendar.getInstance(Locale.KOREA).getTimeInMillis());
    }

    public String makeCarrierUrl(String carrierId, String trackId){
        return String.format(Constants.TRACKER_DELIVERY_URL, carrierId, trackId);
    }

//    @Transactional(readOnly = true)
//    public Order getOrder(String orderNumber){
//        return orderRepository.findByOrderNumberAndUserId(orderNumber, user.getId());
//    }

    public void sendOrderSlackNotification(String username, String userTel, EPaymentType paymentType, String orderNumber, String orderDate, String orderName, String comment, int totalAmount) {
        JSONObject request = SlackUtil.makeOrder(username, userTel, paymentType, orderNumber, orderDate, orderName, totalAmount);
        snsService.sendSlack(SlackChannel.ORDER, request);
    }

    @Transactional
    public OrderSheetTempResponse.NextUrlResponse retrieveOrderTrackerDeliveryUrl(String shippingNumber, Long userId){
        OrderShipping orderShipping = orderShippingRepository.findByUserIdAndShippingNumberFetch(userId, shippingNumber);
        if(orderShipping==null || orderShipping.getCarrier()==null || orderShipping.getTrackingNumber()==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "운송장 번호가 존재하지 않습니다.");
        }
        CarrierTrackResponse trackResponse = thirdPartyService.getCarrierTrackInfo(orderShipping.getCarrier(), orderShipping.getTrackingNumber());
        if(trackResponse!=null){
            if(trackResponse.getState().getId().equals("delivered")){
                if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
                    if(!orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
                        orderShipping.updateShippingDeliveryComplete(LocalDateTimeUtils.stringToLocalDateTime(trackResponse.getTo().getTime()));
                        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), userId, EGofieldService.GOFIELD_API, orderShipping.getStatus());
                        orderShippingLogRepository.save(orderShippingLog);
                    }
                }
            }
        }
        return OrderSheetTempResponse.NextUrlResponse.of(makeCarrierUrl(orderShipping.getCarrier(), orderShipping.getTrackingNumber()));
    }

    @Transactional(readOnly = true)
    public OrderSheetResponse retrieveOrderSheet(String uuid, Long userId) {
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(userId, uuid);
        if (orderSheet == null) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "장바구니 임시 정보가 존재하지 않습니다.");
        }
        OrderSheetContentResponse result = ObjectMapperUtils.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>() {});
        UserAddressResponse userAddressResponse = UserAddressResponse.of(userService.getUserMainAddress(userId));
        return OrderSheetResponse.of(result.getOrderSheetList(), userAddressResponse, orderSheet.getIsOrder());
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse retrieveOrder(String orderNumber, Long userId){
        Order order = orderRepository.findByOrderNumberAndUserIdAndNotStatusDelete(userId, orderNumber);
        if(order==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 존재하지 않는 주문번호입니다.", orderNumber));
        }
        return OrderDetailResponse.of(order, OrderShippingResponse.of(order.getOrderShippings()));
    }

    @Transactional(readOnly = true)
    public OrderShippingDetailResponse retrieveOrderShipping(String orderNumber, String shippingNumber, Long userId){
        OrderShippingAddress orderShippingAddress = orderShippingAddressRepository.findByOrderNumber(orderNumber);
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumberFetch(userId, shippingNumber, orderNumber);
        return OrderShippingDetailResponse.of(orderShipping, orderShippingAddress);
      }

    @Transactional(readOnly = true)
    public OrderListResponse retrieveOrders(EOrderShippingStatusFlag status, Pageable pageable, Long userId){
        List<Order> orderList = orderRepository.findAllByUserIdAndNotStatusDelete(userId, status, pageable);
        return OrderListResponse.of(OrderResponse.of(orderList));
    }

    @Transactional
    public OrderSheetCodeResponse registerOrderSheet(OrderRequest.OrderSheet request, Long userId){
        int totalPrice = 0;
        int totalDelivery = 0;
        Double totalSafeCharge = 0.0;
        List<ItemOrderSheetResponse> result = new ArrayList<>();
        /*
        ToDo: 배송비 정책 정해지면 배송비 반영해서 가격 계산
         */
        for(OrderRequest.OrderSheet.OrderSheetItem sheetItem: request.getItems()){
            ItemOrderSheetProjection itemStock = itemRepository.findItemOrderSheetByItemNumber(userId, request.getIsCart(), sheetItem.getItemNumber());
            if(itemStock==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 존재하지 않는 상품번호입니다.", sheetItem.getItemNumber()));
            }
            if(!itemStock.getStatus().equals(EItemStatusFlag.SALE)){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST,  String.format("<%s>는 판매중이지 않는 상품번호입니다.", sheetItem.getItemNumber()));
            }
            if(sheetItem.getQty()>itemStock.getQty()){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>는 판매 상품 갯수가 초과된 상품입니다.", sheetItem.getItemNumber()));
            }
            int price = 0;
            if(request.getIsCart()){
                price = itemStock.getItemPrice();
            } else {
                if(itemStock.getIsOption()){
                    price = itemStock.getItemPrice() + itemStock.getOptionPrice();
                } else {
                    price = itemStock.getItemPrice();
                }
            }

            int deliveryPrice = 0;

            if(itemStock.getDelivery().equals(EItemDeliveryFlag.PAY) && !itemStock.getIsPaid()){
                deliveryPrice = itemStock.getDeliveryPrice();
            }


//            else if(itemStock.getDelivery().equals(EItemDeliveryFlag.CONDITION)){
//                if(itemStock.getCondition()>=price*sheetItem.getQty()) {
//                    deliveryPrice = itemStock.getCharge();
//                }
//            }
            totalPrice += price*sheetItem.getQty();
            totalDelivery += deliveryPrice;
            ItemOrderSheetResponse orderSheet = ItemOrderSheetResponse.of(itemStock.getId(), itemStock.getSellerId(), itemStock.getBundleId(), itemStock.getCategoryId(), itemStock.getBrandId(), itemStock.getBrandName(), itemStock.getName(), itemStock.getOptionName(), itemStock.getThumbnail(), itemStock.getItemNumber(), price, sheetItem.getQty(), deliveryPrice, itemStock.getOptionId(), itemStock.getIsOption(), itemStock.getDelivery(), itemStock.getOptionType(), itemStock.getChargeType(),  itemStock.getIsPaid(), itemStock.getCharge(), itemStock.getCondition(), itemStock.getFeeJeju(), itemStock.getFeeJejuBesides());
            result.add(orderSheet);
        }
        if(request.getTotalPrice()!=totalPrice){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "총 금액이 맞지 않습니다.");
        }else if(request.getTotalDelivery()!=totalDelivery){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "총 배송 금액이 맞지 않습니다.");
        }
        totalSafeCharge = totalPrice * Constants.SAFE_PAYMENT_CHARGE / 100.0;
        int totalEndPrice = totalPrice+totalDelivery+totalSafeCharge.intValue();
        ItemOrderSheetListResponse list = ItemOrderSheetListResponse.of(totalPrice, totalDelivery, totalSafeCharge, result);
        List<Long> cartIdList = request.getIsCart() ? request.getItems().stream().map(p -> p.getCartId()).collect(Collectors.toList()) : null;
        OrderSheetContentResponse contentResponse = OrderSheetContentResponse.of(makeOrderName(result), list, cartIdList);
        OrderSheet orderSheet = OrderSheet.newInstance(userId, ObjectMapperUtils.objectToJsonStr(contentResponse), totalEndPrice);
        orderSheetRepository.save(orderSheet);
        return OrderSheetCodeResponse.of(orderSheet.getUuid());
    }


    private String makeOrderName(List<ItemOrderSheetResponse> list){
        if(list.size()>1){
           return String.format("%s 외 %s건", list.get(0).getName(), list.size()-1);
        } else {
            return list.get(0).getName();
        }
    }

    @Transactional(readOnly = true)
    public OrderItemResponse retrieveOrderItem(String orderNumber, Long orderItemId, Long userId){
        OrderItem orderItem = orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, userId);
        return OrderItemResponse.of(orderItem);
    }

    @Transactional
    public OrderWaitCreateResponse registerOrderWait(OrderRequest.OrderPay request, Long userId){
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(userId, request.getUuid());
        User user = userService.getUser(userId);
        if(orderSheet==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "주문시트가 존재하지 않습니다.");
        }
        if(orderSheet.getIsOrder()){
            return OrderWaitCreateResponse.of(null, null, null, true);
        }
        OrderSheetContentResponse orderSheetContent = ObjectMapperUtils.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>(){});
        if(request.getPaymentType().equals(EPaymentType.CARD)){
            TossPaymentRequest.Payment externalRequest = TossPaymentRequest.Payment.of(orderSheet.getTotalPrice(), Constants.METHOD, makeOrderNumber(), orderSheetContent.getOrderName(), request.getCompanyCode(), null, thirdPartyService.getTossPaymentSuccessUrl(), thirdPartyService.getTossPaymentFailUrl());
            TossPaymentResponse response = thirdPartyService.getPaymentReadyInfo(externalRequest);
            OrderWait orderWait = OrderWait.newInstance(userId, externalRequest.getOrderId(), orderSheet.getUuid(), ObjectMapperUtils.objectToJsonStr(response), ObjectMapperUtils.objectToJsonStr(request.getShippingAddress()), request.getPaymentType(), request.getEnvironment());
            orderWaitRepository.save(orderWait);
            return OrderWaitCreateResponse.of(request.getPaymentType(), response.getCheckout().getUrl(), null, false);
        } else if(request.getPaymentType().equals(EPaymentType.VIRTUAL_ACCOUNT)){
            if(request.getCustomerName()==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "고객 이름은 필수값입니다.");
            }
            TossPaymentRequest.PaymentVirtual externalRequest = TossPaymentRequest.PaymentVirtual.of(orderSheet.getTotalPrice(), request.getBankCode(), makeOrderNumber(), orderSheetContent.getOrderName(), request.getCustomerName(), request.getCustomerEmail(), user.getTel(),  LocalDateTimeUtils.now().plusDays(1).toString());
            TossPaymentVirtualResponse response = thirdPartyService.getVirtualAccountReadyInfo(externalRequest);
            OrderWait orderWait = OrderWait.newInstance(userId, externalRequest.getOrderId(), orderSheet.getUuid(), ObjectMapperUtils.objectToJsonStr(response), ObjectMapperUtils.objectToJsonStr(request.getShippingAddress()), request.getPaymentType(), request.getEnvironment());
            orderWaitRepository.save(orderWait);
            createOrderByVirtualAccount(request.getShippingAddress(), externalRequest, response, orderWait, orderSheet);
            orderSheet.update();
            return OrderWaitCreateResponse.of(request.getPaymentType(), null, response.getVirtualAccount().getAccountNumber(), true);
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.NONE, "지원하지 않는 결제타입입니다.");
        }
    }


    @Transactional
    public void createOrderByVirtualAccount(UserRequest.ShippingAddress shippingAddress, TossPaymentRequest.PaymentVirtual request, TossPaymentVirtualResponse response, OrderWait orderWait, OrderSheet orderSheet){
        OrderSheetContentResponse orderSheetContent =  ObjectMapperUtils.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>(){});
        ItemOrderSheetListResponse orderSheetList = orderSheetContent.getOrderSheetList();
        OrderShippingAddress orderShippingAddress = OrderShippingAddress.newInstance(response.getOrderId(), shippingAddress.getName(), shippingAddress.getTel(), shippingAddress.getZipCode(), shippingAddress.getAddress(), shippingAddress.getAddressExtra(), shippingAddress.getShippingComment());
        orderShippingAddressRepository.save(orderShippingAddress);

        Order order = Order.newVirtualOrderInstance(orderShippingAddress, orderWait.getUserId(), response.getOrderId(), response.getPaymentKey(), orderSheetList.getTotalPrice(), orderSheet.getTotalPrice(), orderSheetList.getTotalDelivery(),0,  orderSheetList.getTotalSafeCharge(), response.getVirtualAccount().getAccountNumber(), request.getBank(), request.getCustomerEmail(), EPaymentType.VIRTUAL_ACCOUNT.name(), LocalDateTimeUtils.stringToLocalDateTime(response.getVirtualAccount().getDueDate()));
        orderRepository.save(order);

        for(ItemOrderSheetResponse result: orderSheetList.getOrderSheetList()){
            OrderShipping orderShipping = OrderShipping.newInstance(result.getSellerId(), order, response.getOrderId(), RandomUtils.makeRandomCode(32), shippingAddress.getShippingComment(), result.getChargeType(), result.getIsPaid(),  result.getCharge(), result.getDeliveryPrice(), result.getCondition(), result.getFeeJeju(), result.getFeeJejuBesides(), EOrderShippingStatusFlag.ORDER_SHIPPING_WAIT);
            orderShippingRepository.save(orderShipping);
            OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderWait.getUserId(), EGofieldService.GOFIELD_API,  EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK);
            orderShippingLogRepository.save(orderShippingLog);
            ItemStock itemStock = itemStockRepository.findByItemNumber(result.getItemNumber());
            itemStock.updateOrderApprove(result.getQty());
            if(itemStock.getStatus().equals(EItemStatusFlag.SOLD_OUT)){
                ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(itemStock.getItem().getBundle().getId());
                itemBundleAggregation.updateItemMinusOne();
            }
            OrderItemOption orderItemOption = null;
            if(result.getIsOption()){
                ItemOption itemOption = itemOptionRepository.findByOptionId(result.getOptionId());
                orderItemOption = OrderItemOption.newInstance(itemOption.getId(), result.getOptionType(), ObjectMapperUtils.objectToJsonStr(result.getOptionName()), result.getQty(), result.getPrice());
                orderItemOptionRepository.save(orderItemOption);
            }
            OrderItem orderItem = OrderItem.newInstance(order, result.getSellerId(), itemStock.getItem(), orderItemOption, orderShipping, response.getOrderId(), makeOrderNumber(), result.getItemNumber(), result.getName(),  result.getQty(), result.getPrice(), Constants.SAFE_PAYMENT_CHARGE);
            orderItemRepository.save(orderItem);
            updateItemBundleAggregation(result.getBundleId());
        }
        List<Long> cartIdList = orderSheetContent.getCartIdList();
        if(cartIdList!=null && !cartIdList.isEmpty()){
            cartRepository.deleteByCartIdList(cartIdList);
        }
        sendOrderSlackNotification(shippingAddress.getName(), shippingAddress.getTel(), EPaymentType.VIRTUAL_ACCOUNT, order.getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(order.getCreateDate()), orderSheetContent.getOrderName(), null, order.getTotalAmount());
    }

    @Transactional
    public void deleteOrderShipping(String orderNumber, String shippingNumber, Long userId){
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumberFetchOrder(userId, shippingNumber, orderNumber);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 상품 확인중이거나 완료된 배송은 구매 내역 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 준비중인 배송은 구매 내역 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 아직 배송중이어서 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 이미 삭제 처리가 되어 있는 배송 번호입니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 주문 취소 접수가 되어 있어 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 교환 접수가 되어 있어 삭제가 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 반품 접수가 되어 있어 삭제가 불가합니다.", shippingNumber));
        }
        orderShipping.updateDelete();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), userId, EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE);
        orderShippingLogRepository.save(orderShippingLog);
    }

    @Transactional
    public void completeOrderShipping(String orderNumber, String shippingNumber, Long userId){
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumberFetchOrder(userId, shippingNumber, orderNumber);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 상품 확인중이거나 완료된 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 준비중인 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 삭제 처리된 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 취소 처리된 배송은 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 교환 접수가 되어 있어 구매 확정이 불가합니다.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> 반품 접수가 되어 있어 구매 확정이 불가합니다.", shippingNumber));
        }
        orderShipping.updateShippingComplete();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), userId, EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE);
        orderShippingLogRepository.save(orderShippingLog);
    }

    @Transactional(readOnly = true)
    public OrderItemReviewListResponse retrieveOrderItemReviews(Pageable pageable, Long userId){
        List<OrderItemProjection> result = orderItemRepository.findAllByUserIdAndShippingStatusShippingComplete(userId, pageable);
        List<OrderItemReviewResponse> response = OrderItemReviewResponse.of(result);
        return OrderItemReviewListResponse.of(response);
    }

    @Transactional
    public OrderItemReviewDetailListResponse retrieveReviews(Pageable pageable, Long userId){
        List<ItemBundleReview> result = itemBundleReviewRepository.findAllByUserIdFetch(userId, pageable);
        List<OrderItemReviewDetailResponse> response = OrderItemReviewDetailResponse.of(result);
        return OrderItemReviewDetailListResponse.of(response);
    }

    @Transactional
    public void reviewOrderItem(Long orderItemId, OrderRequest.OrderReview request, List<MultipartFile> files, Long userId){
        User user = userService.getUser(userId);
        OrderItem orderItem = orderItemRepository.findByOrderItemId(orderItemId);
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("%s 존재하지 않는 주문 상품 아이디입니다.", orderItemId));
        }
        Order order = orderRepository.findByOrderNumberAndUserId(orderItem.getOrderNumber(), userId);
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
                String image = s3FileStorageClient.uploadFile(file, FileType.ITEM_BUNDLE_REVIEW_IMAGE);
                imageList.add(image);
            }
        }
        int qty = orderItem.getOrderItemOption()!=null ? orderItem.getOrderItemOption().getQty() : orderItem.getQty();
        int price = orderItem.getOrderItemOption()!=null ? orderItem.getOrderItemOption().getPrice() : orderItem.getPrice();
        ItemBundleReview itemBundleReview = ItemBundleReview.newInstance(orderItem.getItem().getBundle(), userId, order.getId(), orderItem.getItemNumber(), orderItem.getName(), user.getNickName(), orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getName(), orderItem.getItem().getThumbnail(), request.getWeight(), request.getHeight(), request.getReviewScore(), price, qty, request.getContent());
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
        orderItem.getOrderShipping().updateShippingComplete();
    }

    @Transactional(readOnly = true)
    public OrderCancelItemTempResponse retrieveOrderItemBeforeCancel(Long orderItemId, Long userId){
        String refundBank = null;
        String refundName = null;
        String refundAccount = null;
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, userId);
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id는 존재하지 않는 주문 상품 번호입니다.", orderItemId));
        }
        if(orderItem.getOrder().getPaymentType().equals("VIRTUAL_ACCOUNT")) {
            refundName = orderItem.getOrder().getDepositorName();
            refundBank = orderItem.getOrder().getBankName();
            refundAccount = orderItem.getOrder().getVirtualAccountNumber();
        }
        return OrderCancelItemTempResponse.of(orderItem, refundName, refundBank, refundAccount);
    }

    @Transactional
    public void changeOrder(Long orderItemId, OrderRequest.OrderChange request, Long userId){
        User user = userService.getUser(userId);
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, user.getId());
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id는 존재하지 않는 주문 상품 번호입니다.", orderItemId));
        }
        validateOrderShippingReturnAndChangeStatus(orderItem.getOrderShipping().getStatus());
        OrderCancelItemTempResponse orderItemInfo = OrderCancelItemTempResponse.of(orderItem,null, null, null);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, request.getUsername(), request.getUserTel(), request.getZipCode(), request.getAddress(), request.getAddressExtra(), request.getContent());
        orderCancelCommentRepository.save(orderCancelComment);
        OrderCancel orderCancel = OrderCancel.newChangeInstance(orderItem.getOrder(), orderItem.getOrderShipping(), orderCancelComment, orderItem.getItem().getShippingTemplate(), makeOrderCancelNumber(), EOrderCancelCodeFlag.USER, request.getReason(), orderItemInfo.getTotalAmount(), orderItemInfo.getItemPrice(), orderItemInfo.getDeliveryPrice(), orderItemInfo.getRefundPrice());
        Item item = orderItemInfo.getIsOption() ? null : itemRepository.findByItemId(orderItemInfo.getItemId());
        ItemOption itemOption = orderItemInfo.getIsOption() ? itemOptionRepository.findByOptionId(orderItemInfo.getItemOptionId()) : null;
        EOrderCancelItemFlag itemType = orderItemInfo.getIsOption() ? EOrderCancelItemFlag.ORDER_ITEM_OPTION : EOrderCancelItemFlag.ORDER_ITEM;
        OrderCancelItem orderCancelItem = OrderCancelItem.newInstance(orderCancel, item, itemOption, orderItemInfo.getName(), orderItemInfo.getOptionName()==null ? null : orderItemInfo.getOptionName(), itemType, orderItemInfo.getQty(), orderItemInfo.getItemPrice());
        orderCancel.addOrderCancelItem(orderCancelItem);
        orderCancelRepository.save(orderCancel);
        orderItem.getOrderShipping().updateChange();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderItem.getOrderShipping().getId(), user.getId(), EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE);
        String thumbnail = orderItemInfo.getIsOption() ? itemOption.getItem().getThumbnail() : item.getThumbnail();
        orderShippingLogRepository.save(orderShippingLog);
        thirdPartyService.sendCancelSlackNotification(SlackChannel.CHANGE, orderCancelComment.getName(), orderCancelComment.getTel(), orderCancel.getOrder().getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(orderCancel.getCreateDate()), orderCancelComment.getContent(), orderCancelItem.getName(), orderCancelItem.getOptionName(), CommonUtils.makeCloudFrontAdminUrl(thumbnail), orderCancel.getTotalAmount());
    }


    @Transactional
    public void returnOrder(Long orderItemId, OrderRequest.OrderReturn request, Long userId) {
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, userId);
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id는 존재하지 않는 주문 상품 번호입니다.", orderItemId));
        }
        validateOrderShippingReturnAndChangeStatus(orderItem.getOrderShipping().getStatus());
        String refundName = null;
        String refundAccount = null;
        String refundBank = null;
        if(orderItem.getOrder().getPaymentType().equals("VIRTUAL_ACCOUNT")) {
            refundName = orderItem.getOrder().getDepositorName();
            refundBank = orderItem.getOrder().getBankName();
            refundAccount = orderItem.getOrder().getVirtualAccountNumber();
        }
        User user = userService.getUser(userId);
        OrderCancelItemTempResponse orderItemInfo = OrderCancelItemTempResponse.of(orderItem, refundName, refundAccount, refundBank);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, "정보없음", "정보없음", "정보없음", "정보없음", "정보없음", request.getContent());
        OrderCancel orderCancel = OrderCancel.newReturnInstance(orderItem.getOrder(), orderItem.getOrderShipping(), orderCancelComment, orderItem.getItem().getShippingTemplate(), makeOrderCancelNumber(), EOrderCancelCodeFlag.USER, request.getReason(), orderItemInfo.getTotalAmount(), orderItemInfo.getItemPrice(), orderItemInfo.getDeliveryPrice(), orderItemInfo.getDiscountPrice(), orderItemInfo.getRefundPrice(), orderItemInfo.getTotalAmount(),  orderItemInfo.getRefundName(), orderItemInfo.getRefundAccount(), orderItemInfo.getRefundBank());
        Item item = orderItemInfo.getIsOption() ? null : itemRepository.findByItemId(orderItemInfo.getItemId());
        ItemOption itemOption = orderItemInfo.getIsOption() ? itemOptionRepository.findByOptionId(orderItemInfo.getItemOptionId()) : null;
        EOrderCancelItemFlag itemType = orderItemInfo.getIsOption() ? EOrderCancelItemFlag.ORDER_ITEM_OPTION : EOrderCancelItemFlag.ORDER_ITEM;
        OrderCancelItem orderCancelItem = OrderCancelItem.newInstance(orderCancel, item, itemOption, orderItemInfo.getName(), orderItemInfo.getOptionName()==null ? null : orderItemInfo.getOptionName(), itemType, orderItemInfo.getQty(), orderItemInfo.getItemPrice());
        orderCancel.addOrderCancelItem(orderCancelItem);
        orderCancelRepository.save(orderCancel);
        orderItem.getOrderShipping().updateReturn();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderItem.getOrderShipping().getId(), user.getId(), EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN);
        orderShippingLogRepository.save(orderShippingLog);
        String thumbnail = orderItemInfo.getIsOption() ? itemOption.getItem().getThumbnail() : item.getThumbnail();
        thirdPartyService.sendCancelSlackNotification(SlackChannel.RETURN, orderCancelComment.getName(), orderCancelComment.getTel(), orderCancel.getOrder().getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(orderCancel.getCreateDate()), orderCancelComment.getContent(), orderCancelItem.getName(), orderCancelItem.getOptionName(), CommonUtils.makeCloudFrontAdminUrl(thumbnail), orderCancel.getTotalAmount());
    }

    @Transactional
    public void cancelOrder(Long orderItemId, OrderRequest.OrderCancel request, Long userId){
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, userId);
        User user = userService.getUser(userId);
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id는 존재하지 않는 주문 상품 번호입니다.", orderItemId));
        }
        String refundName = null;
        String refundAccount = null;
        String refundBank = null;
        if(orderItem.getOrder().getPaymentType().equals("VIRTUAL_ACCOUNT")) {
            refundName = orderItem.getOrder().getDepositorName();
            refundBank = orderItem.getOrder().getBankName();
            refundAccount = orderItem.getOrder().getVirtualAccountNumber();
        }
        OrderShippingAddress orderShippingAddress = orderItem.getOrder().getShippingAddress();
        OrderCancelItemTempResponse orderItemInfo = OrderCancelItemTempResponse.of(orderItem, refundName, refundAccount, refundBank);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, orderShippingAddress.getName(), orderShippingAddress.getTel(), orderShippingAddress.getZipCode(), orderShippingAddress.getAddress(), orderShippingAddress.getAddressExtra(), request.getReason().getDescription());
        orderCancelCommentRepository.save(orderCancelComment);
        OrderCancel orderCancel = OrderCancel.newCancelInstance(orderItem.getOrder(), orderItem.getOrderShipping(), orderCancelComment, orderItem.getItem().getShippingTemplate(), makeOrderCancelNumber(), EOrderCancelCodeFlag.USER, request.getReason(), orderItemInfo.getTotalAmount(), orderItemInfo.getItemPrice(), orderItemInfo.getDeliveryPrice(), orderItemInfo.getDiscountPrice(), orderItemInfo.getRefundPrice(), orderItemInfo.getSafeChargePrice(),  orderItemInfo.getTotalAmount(),  orderItemInfo.getRefundName(), orderItemInfo.getRefundAccount(), orderItemInfo.getRefundBank());
        Item item = orderItemInfo.getIsOption() ? null : itemRepository.findByItemId(orderItemInfo.getItemId());
        ItemOption itemOption = orderItemInfo.getIsOption() ? itemOptionRepository.findByOptionId(orderItemInfo.getItemOptionId()) : null;
        EOrderCancelItemFlag itemType = orderItemInfo.getIsOption() ? EOrderCancelItemFlag.ORDER_ITEM_OPTION : EOrderCancelItemFlag.ORDER_ITEM;
        OrderCancelItem orderCancelItem = OrderCancelItem.newInstance(orderCancel, item, itemOption, orderItemInfo.getName(), orderItemInfo.getOptionName()==null ? null : orderItemInfo.getOptionName(), itemType, orderItemInfo.getQty(), orderItemInfo.getItemPrice());
        orderCancel.addOrderCancelItem(orderCancelItem);
        orderCancelRepository.save(orderCancel);
        orderItem.getOrderShipping().updateCancel();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderItem.getOrderShipping().getId(), user.getId(), EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL);
        orderShippingLogRepository.save(orderShippingLog);
        String thumbnail = orderItemInfo.getIsOption() ? itemOption.getItem().getThumbnail() : item.getThumbnail();
        thirdPartyService.sendCancelSlackNotification(SlackChannel.CANCEL, orderCancelComment.getName(), orderCancelComment.getTel(), orderCancel.getOrder().getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(orderCancel.getCreateDate()), orderCancelComment.getContent(), orderCancelItem.getName(), orderCancelItem.getOptionName(), CommonUtils.makeCloudFrontAdminUrl(thumbnail), orderCancel.getTotalAmount());
    }

    @Transactional(readOnly = true)
    public OrderCancelListResponse retrieveCancels(Pageable pageable, Long userId){
        Page<OrderCancel> result = orderCancelRepository.findAllFetchJoin(userId, pageable);
        List<OrderCancelResponse> response = OrderCancelResponse.of(result.getContent());
        PaginationResponse page = PaginationResponse.of(result);
        return OrderCancelListResponse.of(response, page);
    }

    @Transactional(readOnly = true)
    public OrderCancelDetailResponse retrieveCancel(Long cancelId, Long userId){
        OrderCancel result = orderCancelRepository.findByCancelIdAndUserIdFetchJoin(cancelId, userId);
        if(result==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("%s는 존재하지 않는 주문 아이디입니다.", cancelId));
        }
        return OrderCancelDetailResponse.of(result);
    }

    public void updateItemBundleAggregation(Long bundleId){
        if(bundleId.equals(100000L)) return;
        //묶음 집계 업데이트
        ItemBundleAggregation itemBundleAggregation = itemBundleAggregationRepository.findByBundleId(bundleId);

        List<Item> itemList = itemRepository.findAllItemByBundleIdAndStatusSalesOrderByPrice(bundleId);
        List<Item> usedItemList = itemList.stream().filter(k -> k.getClassification().equals(EItemClassificationFlag.USED)).collect(Collectors.toList());
        List<Item> newItemList = itemList.stream().filter(k -> k.getClassification().equals(EItemClassificationFlag.NEW)).collect(Collectors.toList());

        int itemCount = 0;
        int newLowestPrice = 0;
        int usedLowestPrice = 0;
        int lowestPrice = 0;
        int highestPrice = 0;

        if(itemList.isEmpty()){
            itemBundleAggregation.update(0, 0, 0, 0, 0);
        } else {
            itemCount = itemList.size();
            lowestPrice = itemList.get(0).getPrice();
            highestPrice = itemList.get(itemList.size()-1).getPrice();
        }
        if(usedItemList.isEmpty()){
            usedLowestPrice = 0;
        } else {
            usedLowestPrice = usedItemList.get(0).getPrice();
        }
        if(newItemList.isEmpty()){
            newLowestPrice = 0;
        } else {
            newLowestPrice = newItemList.get(0).getPrice();
        }
        itemBundleAggregation.update(itemCount, newLowestPrice, usedLowestPrice, lowestPrice, highestPrice);
    }
}
