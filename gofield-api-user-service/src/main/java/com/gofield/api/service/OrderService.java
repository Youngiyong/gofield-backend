package com.gofield.api.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.dto.enums.PaymentType;
import com.gofield.api.dto.enums.SlackChannelType;
import com.gofield.api.dto.req.OrderRequest;
import com.gofield.api.dto.res.*;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.exception.*;
import com.gofield.common.model.Constants;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.CommonUtils;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.domain.rds.domain.common.EGofieldService;
import com.gofield.domain.rds.domain.common.PaginationResponse;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemBundleReviewScoreProjection;
import com.gofield.domain.rds.domain.item.projection.ItemOrderSheetProjection;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.order.projection.OrderItemProjection;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.user.UserAccount;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentResponse;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import com.gofield.infrastructure.s3.infra.S3FileStorageClient;
import com.gofield.infrastructure.s3.model.enums.FileType;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    private final ItemOptionRepository itemOptionRepository;
    private final OrderWaitRepository orderWaitRepository;
    private final OrderRepository orderRepository;
    private final OrderShippingLogRepository orderShippingLogRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final OrderCancelRepository orderCancelRepository;
    private final OrderCancelCommentRepository orderCancelCommentRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingAddressRepository orderShippingAddressRepository;
    private final ItemBundleReviewRepository itemBundleReviewRepository;
    private final ItemBundleAggregationRepository itemBundleAggregationRepository;
    private final UserService userService;
    private final ThirdPartyService thirdPartyService;
    private final S3FileStorageClient s3FileStorageClient;

    private void validateOrderShippingReturnAndChangeStatus(EOrderShippingStatusFlag status){
        if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ????????? ????????? ??????/?????? ????????? ???????????????.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ?????? ????????? ????????? ???????????????.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ?????? ????????? ????????? ???????????????.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ????????? ???????????? ???????????????.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ????????? ????????? ???????????????.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ?????? ????????? ???????????? ???????????????.");
        } else if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ????????? ????????? ???????????????.");
        } else {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "??????/?????? ????????? ????????? ???????????????.");
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

    @Transactional(readOnly = true)
    public Order getOrder(String orderNumber){
        User user = userService.getUserNotNonUser();
        return orderRepository.findByOrderNumberAndUserId(orderNumber, user.getId());
    }

    @Transactional
    public NextUrlResponse getOrderTrackerDeliveryUrl(String shippingNumber){
        User user = userService.getUserNotNonUser();
        OrderShipping orderShipping = orderShippingRepository.findByUserIdAndShippingNumberFetch(user.getId(), shippingNumber);
        if(orderShipping==null || orderShipping.getCarrier()==null || orderShipping.getTrackingNumber()==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "????????? ????????? ???????????? ????????????.");
        }
        CarrierTrackResponse trackResponse = thirdPartyService.getCarrierTrackInfo(orderShipping.getCarrier(), orderShipping.getTrackingNumber());
        if(trackResponse!=null){
            if(trackResponse.getState().getId().equals("delivered")){
                if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
                    if(!orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
                        orderShipping.updateShippingDeliveryComplete(LocalDateTimeUtils.stringToLocalDateTime(trackResponse.getTo().getTime()));
                        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_API, orderShipping.getStatus());
                        orderShippingLogRepository.save(orderShippingLog);
                    }
                }
            }
        }
        return NextUrlResponse.of(makeCarrierUrl(orderShipping.getCarrier(), orderShipping.getTrackingNumber()));
    }

    @Transactional(readOnly = true)
    public OrderSheetResponse getOrderSheet(String uuid) {
        User user = userService.getUserNotNonUser();
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), uuid);
        if (orderSheet == null) {
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "???????????? ?????? ????????? ???????????? ????????????.");
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
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ???????????? ?????? ?????????????????????.", orderNumber));
        }
        return OrderDetailResponse.of(order, OrderShippingResponse.of(order.getOrderShippings()));
    }

    @Transactional(readOnly = true)
    public OrderShippingDetailResponse getOrderShipping(String orderNumber, String shippingNumber){
        User user = userService.getUserNotNonUser();
        OrderShippingAddress orderShippingAddress = orderShippingAddressRepository.findByOrderNumber(orderNumber);
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumberFetch(user.getId(), shippingNumber, orderNumber);
        return OrderShippingDetailResponse.of(orderShipping, orderShippingAddress);
      }

    @Transactional(readOnly = true)
    public OrderListResponse getOrderList(EOrderShippingStatusFlag status, Pageable pageable){
        User user = userService.getUserNotNonUser();
        List<Order> orderList = orderRepository.findAllByUserIdAndNotStatusDelete(user.getId(), status, pageable);
        return OrderListResponse.of(OrderResponse.of(orderList));
    }

    @Transactional
    public CommonCodeResponse createOrderSheet(OrderRequest.OrderSheet request){
        User user = userService.getUserNotNonUser();
        int totalPrice = 0;
        int totalDelivery = 0;
        List<ItemOrderSheetResponse> result = new ArrayList<>();
        /*
        ToDo: ????????? ?????? ???????????? ????????? ???????????? ?????? ??????
         */
        for(OrderRequest.OrderSheet.OrderSheetItem sheetItem: request.getItems()){
            ItemOrderSheetProjection itemStock = itemRepository.findItemOrderSheetByItemNumber(user.getId(), request.getIsCart(), sheetItem.getItemNumber());
            if(itemStock==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>??? ???????????? ?????? ?????????????????????.", sheetItem.getItemNumber()));
            }
            if(!itemStock.getStatus().equals(EItemStatusFlag.SALE)){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST,  String.format("<%s>??? ??????????????? ?????? ?????????????????????.", sheetItem.getItemNumber()));
            }
            if(sheetItem.getQty()>itemStock.getQty()){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s>??? ?????? ?????? ????????? ????????? ???????????????.", sheetItem.getItemNumber()));
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

            if(itemStock.getDelivery().equals(EItemDeliveryFlag.PAY)){
                deliveryPrice = itemStock.getDeliveryPrice();
            } else if(itemStock.getDelivery().equals(EItemDeliveryFlag.CONDITION)){
                if(itemStock.getCondition()>=price*sheetItem.getQty()) {
                    deliveryPrice = itemStock.getCharge();
                }
            }
            totalPrice += price*sheetItem.getQty();
            totalDelivery += deliveryPrice;
            ItemOrderSheetResponse orderSheet = ItemOrderSheetResponse.of(itemStock.getId(), itemStock.getSellerId(), itemStock.getBundleId(), itemStock.getBrandName(), itemStock.getName(), itemStock.getOptionName(), itemStock.getThumbnail(), itemStock.getItemNumber(), price, sheetItem.getQty(), deliveryPrice, itemStock.getOptionId(), itemStock.getIsOption(), itemStock.getOptionType(), itemStock.getChargeType(), itemStock.getCharge(), itemStock.getCondition(), itemStock.getFeeJeju(), itemStock.getFeeJejuBesides());
            result.add(orderSheet);
        }
        if(request.getTotalPrice()!=totalPrice){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "??? ????????? ?????? ????????????.");
        }else if(request.getTotalDelivery()!=totalDelivery){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "??? ?????? ????????? ?????? ????????????.");
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
           return String.format("%s ??? %s???", list.get(0).getName(), list.size()-1);
        } else {
            return list.get(0).getName();
        }
    }

    @Transactional(readOnly = true)
    public OrderItemResponse getOrderItem(String orderNumber, Long orderItemId){
        User user = userService.getUserNotNonUser();
        OrderItem orderItem = orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, user.getId());
        return OrderItemResponse.of(orderItem);
    }

    @Transactional
    public NextUrlResponse createOrderWait(OrderRequest.OrderPay request){
        User user = userService.getUserNotNonUser();
        OrderSheet orderSheet = orderSheetRepository.findByUserIdAndUuid(user.getId(), request.getUuid());
        if(orderSheet==null){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "??????????????? ???????????? ????????????.");
        }
        OrderSheetContentResponse orderSheetContent = ApiUtil.strToObject(orderSheet.getSheet(), new TypeReference<OrderSheetContentResponse>(){});
        String cardCompany = request.getPaymentType().equals(PaymentType.CARD) ? request.getCompanyCode() : null;
        String easyPay = request.getPaymentType().equals(PaymentType.EASYPAY) ? request.getCompanyCode() : null;
        TossPaymentRequest.Payment externalRequest = TossPaymentRequest.Payment.of(orderSheet.getTotalPrice(), Constants.METHOD, makeOrderNumber(), orderSheetContent.getOrderName(), cardCompany, easyPay, thirdPartyService.getTossPaymentSuccessUrl(), thirdPartyService.getTossPaymentFailUrl());
        TossPaymentResponse response = thirdPartyService.getPaymentReadyInfo(externalRequest);
        OrderWait orderWait = OrderWait.newInstance(user.getId(), externalRequest.getOrderId(), orderSheet.getUuid(),  new Gson().toJson(response), new Gson().toJson(request.getShippingAddress()), request.getPaymentType(), request.getEnvironment());
        orderWaitRepository.save(orderWait);
        return NextUrlResponse.of(response.getCheckout().getUrl());
    }

    @Transactional
    public void deleteOrderShipping(String orderNumber, String shippingNumber){
        User user = userService.getUserNotNonUser();
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumberFetchOrder(user.getId(), shippingNumber, orderNumber);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ?????????????????? ????????? ????????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ???????????? ????????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ?????????????????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ?????? ????????? ?????? ?????? ?????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ?????? ????????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ????????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ????????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        }
        orderShipping.updateDelete();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE);
        orderShippingLogRepository.save(orderShippingLog);
    }

    @Transactional
    public void completeOrderShipping(String orderNumber, String shippingNumber){
        User user = userService.getUserNotNonUser();
        OrderShipping orderShipping = orderShippingRepository.findByShippingNumberAndOrderNumberFetchOrder(user.getId(), shippingNumber, orderNumber);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ?????????????????? ????????? ????????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ???????????? ????????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ????????? ????????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ????????? ????????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ????????? ?????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("<%s> ?????? ????????? ?????? ?????? ?????? ????????? ???????????????.", shippingNumber));
        }
        orderShipping.updateShippingComplete();
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_API, EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE);
        orderShippingLogRepository.save(orderShippingLog);
    }

    @Transactional(readOnly = true)
    public OrderItemReviewListResponse getOrderItemReviewList(Pageable pageable){
        User user = userService.getUserNotNonUser();
        List<OrderItemProjection> result = orderItemRepository.findAllByUserIdAndShippingStatusShippingComplete(user.getId(), pageable);
        List<OrderItemReviewResponse> response = OrderItemReviewResponse.of(result);
        return OrderItemReviewListResponse.of(response);
    }

    @Transactional
    public OrderItemReviewDetailListResponse getOrderItemReviewDetailList(Pageable pageable){
        User user = userService.getUserNotNonUser();
        List<ItemBundleReview> result = itemBundleReviewRepository.findAllByUserIdFetch(user.getId(), pageable);
        List<OrderItemReviewDetailResponse> response = OrderItemReviewDetailResponse.of(result);
        return OrderItemReviewDetailListResponse.of(response);
    }

    @Transactional
    public void reviewOrderShippingItem(Long orderItemId, OrderRequest.OrderReview request, List<MultipartFile> files){
       /*
       ToDo: ????????? ???????????? ????????? ???????????? api ??????
        */
        User user = userService.getUserNotNonUser();
        OrderItem orderItem = orderItemRepository.findByOrderItemId(orderItemId);
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("%s ???????????? ?????? ?????? ?????? ??????????????????.", orderItemId));
        }
        Order order = orderRepository.findByOrderNumberAndUserId(orderItem.getOrderNumber(), user.getId());
        if(order==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, "???????????? ?????? ?????????????????????.");
        }
        if(orderItem.getIsReview()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ????????? ?????? ????????? ????????????.");
        }
        if(!orderItem.getOrderShipping().isShippingReview()){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ????????? ????????? ???????????? ?????? ????????? ????????? ????????????.");
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
        ItemBundleReview itemBundleReview = ItemBundleReview.newInstance(orderItem.getItem().getBundle(), user.getId(), order.getId(), orderItem.getItemNumber(), orderItem.getName(), user.getNickName(), orderItem.getOrderItemOption()==null ? null : orderItem.getOrderItemOption().getName(), orderItem.getItem().getThumbnail(), request.getWeight(), request.getHeight(), request.getReviewScore(), price, qty, request.getContent());
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
    public OrderCancelItemTempResponse getOrderItemCancelAndChangeAndReturnTemp(Long orderItemId){
        User user = userService.getUserNotNonUser();
        UserAccount userAccount = null;
        String refundBank = null;
        String refundName = null;
        String refundAccount = null;
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, user.getId());
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id??? ???????????? ?????? ?????? ?????? ???????????????.", orderItemId));
        }
        if(orderItem.getOrder().getPaymentType().equals("BANK")){
            userAccount = userService.getUserAccount(user.getId());
            if(userAccount==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "?????? ?????? ????????? ?????? ????????? ???????????????.");
            }
            refundName = userAccount.getBankHolderName();
            refundBank = userAccount.getBankName();
            refundAccount = userAccount.getBankAccountNumber();
        }
        return OrderCancelItemTempResponse.of(orderItem, refundName, refundBank, refundAccount);
    }

    @Transactional
    public void createOrderChange(Long orderItemId, OrderRequest.OrderChange request){
        User user = userService.getUserNotNonUser();
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, user.getId());
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id??? ???????????? ?????? ?????? ?????? ???????????????.", orderItemId));
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
        thirdPartyService.sendCancelSlackNotification(SlackChannelType.CHANGE, orderCancelComment.getName(), orderCancelComment.getTel(), orderCancel.getOrder().getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(orderCancel.getCreateDate()), orderCancelComment.getContent(), orderCancelItem.getName(), orderCancelItem.getOptionName(), CommonUtils.makeCloudFrontAdminUrl(thumbnail), orderCancel.getTotalAmount());
    }


    @Transactional
    public void createOrderReturn(Long orderItemId, OrderRequest.OrderReturn request) {
        User user = userService.getUserNotNonUser();
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, user.getId());
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id??? ???????????? ?????? ?????? ?????? ???????????????.", orderItemId));
        }
        validateOrderShippingReturnAndChangeStatus(orderItem.getOrderShipping().getStatus());
        String refundName = null;
        String refundAccount = null;
        String refundBank = null;
        if(orderItem.getOrder().getPaymentType().equals("BANK")){
            UserAccount userAccount = userService.getUserAccount(user.getId());
            refundName = userAccount.getBankHolderName();
            refundBank = userAccount.getBankName();
            refundAccount = userAccount.getBankAccountNumber();
        }
        OrderCancelItemTempResponse orderItemInfo = OrderCancelItemTempResponse.of(orderItem, refundName, refundAccount, refundBank);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, request.getUsername(), request.getUserTel(), request.getZipCode(), request.getAddress(), request.getAddressExtra(), request.getReason().getDescription());
        orderCancelCommentRepository.save(orderCancelComment);
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
        thirdPartyService.sendCancelSlackNotification(SlackChannelType.RETURN, orderCancelComment.getName(), orderCancelComment.getTel(), orderCancel.getOrder().getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(orderCancel.getCreateDate()), orderCancelComment.getContent(), orderCancelItem.getName(), orderCancelItem.getOptionName(), CommonUtils.makeCloudFrontAdminUrl(thumbnail), orderCancel.getTotalAmount());
    }

    @Transactional
    public void createOrderCancel(Long orderItemId, OrderRequest.OrderCancel request){
        User user = userService.getUserNotNonUser();
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdAndUserIdFetch(orderItemId, user.getId());
        if(orderItem==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("<%s> Id??? ???????????? ?????? ?????? ?????? ???????????????.", orderItemId));
        }
        String refundName = null;
        String refundAccount = null;
        String refundBank = null;
        if(orderItem.getOrder().getPaymentType().equals("BANK")){
            UserAccount userAccount = userService.getUserAccount(user.getId());
            refundName = userAccount.getBankHolderName();
            refundBank = userAccount.getBankName();
            refundAccount = userAccount.getBankAccountNumber();
        }
        OrderShippingAddress orderShippingAddress = orderItem.getOrder().getShippingAddress();
        OrderCancelItemTempResponse orderItemInfo = OrderCancelItemTempResponse.of(orderItem, refundName, refundAccount, refundBank);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, orderShippingAddress.getName(), orderShippingAddress.getTel(), orderShippingAddress.getZipCode(), orderShippingAddress.getAddress(), orderShippingAddress.getAddressExtra(), request.getReason().getDescription());
        orderCancelCommentRepository.save(orderCancelComment);
        OrderCancel orderCancel = OrderCancel.newCancelInstance(orderItem.getOrder(), orderItem.getOrderShipping(), orderCancelComment, orderItem.getItem().getShippingTemplate(), makeOrderCancelNumber(), EOrderCancelCodeFlag.USER, request.getReason(), orderItemInfo.getTotalAmount(), orderItemInfo.getItemPrice(), orderItemInfo.getDeliveryPrice(), orderItemInfo.getDiscountPrice(), orderItemInfo.getRefundPrice(), orderItemInfo.getTotalAmount(),  orderItemInfo.getRefundName(), orderItemInfo.getRefundAccount(), orderItemInfo.getRefundBank());
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
        thirdPartyService.sendCancelSlackNotification(SlackChannelType.CANCEL, orderCancelComment.getName(), orderCancelComment.getTel(), orderCancel.getOrder().getOrderNumber(), LocalDateTimeUtils.LocalDateTimeToString(orderCancel.getCreateDate()), orderCancelComment.getContent(), orderCancelItem.getName(), orderCancelItem.getOptionName(), CommonUtils.makeCloudFrontAdminUrl(thumbnail), orderCancel.getTotalAmount());
    }

    @Transactional(readOnly = true)
    public OrderCancelListResponse getOrderCancelList(Pageable pageable){
        User user = userService.getUserNotNonUser();
        Page<OrderCancel> result = orderCancelRepository.findAllFetchJoin(user.getId(), pageable);
        List<OrderCancelResponse> response = OrderCancelResponse.of(result.getContent());
        PaginationResponse page = PaginationResponse.of(result);
        return OrderCancelListResponse.of(response, page);
    }

    @Transactional(readOnly = true)
    public OrderCancelDetailResponse getOrderCancel(Long cancelId){
        User user = userService.getUserNotNonUser();
        OrderCancel result = orderCancelRepository.findByCancelIdAndUserIdFetchJoin(cancelId, user.getId());
        if(result==null){
            throw new NotFoundException(ErrorCode.E404_NOT_FOUND_EXCEPTION, ErrorAction.TOAST, String.format("%s??? ???????????? ?????? ?????? ??????????????????.", cancelId));
        }
        return OrderCancelDetailResponse.of(result);
    }
}
