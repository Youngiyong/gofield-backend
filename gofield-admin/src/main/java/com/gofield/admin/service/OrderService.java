package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.admin.util.AdminUtil;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.common.utils.LocalDateTimeUtils;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.common.EGofieldService;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.order.*;
import com.gofield.domain.rds.domain.order.projection.OrderCancelInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderChangeInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderReturnInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.order.projection.OrderShippingInfoAdminProjectionResponse;
import com.gofield.domain.rds.domain.user.User;
import com.gofield.domain.rds.domain.user.UserAccount;
import com.gofield.domain.rds.domain.user.UserAccountRepository;
import com.gofield.domain.rds.domain.user.UserRepository;
import com.gofield.infrastructure.external.api.toss.dto.req.TossPaymentRequest;
import com.gofield.infrastructure.external.api.toss.dto.res.TossPaymentCancelResponse;
import com.gofield.infrastructure.external.api.tracker.res.CarrierTrackResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final ItemRepository itemRepository;
    private final ItemStockRepository itemStockRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderCancelRepository orderCancelRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingLogRepository orderShippingLogRepository;
    private final OrderCancelCommentRepository orderCancelCommentRepository;
    private final PurchaseCancelRepository purchaseCancelRepository;
    private final ThirdPartyService thirdPartyService;

    public String makeOrderCancelNumber() {
        StringBuilder cancelNumber =  new StringBuilder(String.valueOf(Calendar.getInstance(Locale.KOREA).getTimeInMillis()));
        cancelNumber.setCharAt(0, '2');
        return cancelNumber.toString();
    }

    @Transactional(readOnly = true)
    public OrderShippingListDto getOrderList(String keyword, EOrderShippingStatusFlag status, Pageable pageable){
        OrderShippingInfoAdminProjectionResponse page = orderShippingRepository.findAllOrderShippingByKeyword(keyword, status, pageable);
        List<CodeDto> codeList = CodeDto.of(codeRepository.findAllByGroupByIsHide(ECodeGroup.CARRIER, false));
        List<OrderShippingDto> result = OrderShippingDto.of(page.getPage().getContent(), codeList);
        return OrderShippingListDto.of(result, page.getPage(), page.getAllCount(), page.getReceiptCount(), page.getReadyCount(), page.getDeliveryCount(), page.getDeliveryCompleteCount());
    }

    @Transactional(readOnly = true)
    public OrderCancelListDto getOrderCancelList(String keyword, EOrderCancelStatusFlag status, Pageable pageable){
        OrderCancelInfoAdminProjectionResponse page = orderCancelRepository.findAllOrderCancelByKeyword(keyword, status, pageable);
        List<OrderCancelDto> result = OrderCancelDto.of(page.getPage().getContent());
        return OrderCancelListDto.of(result, page.getPage(), page.getAllCount(), page.getReceiptCount(), page.getRefuseCount(), page.getCompleteCount());
    }


    @Transactional(readOnly = true)
    public OrderChangeListDto getOrderChangeList(String keyword, EOrderCancelStatusFlag status, Pageable pageable){
        OrderChangeInfoAdminProjectionResponse page = orderCancelRepository.findAllOrderChangeByKeyword(keyword, status, pageable);
        List<OrderChangeDto> result = OrderChangeDto.of(page.getPage().getContent());
        return OrderChangeListDto.of(result, page.getPage(), page.getAllCount(), page.getReceiptCount(), page.getRefuseCount(), page.getCollectCount(), page.getCollectCompleteCount(), page.getReDeliveryCount(), page.getCompleteCount());
    }

    @Transactional(readOnly = true)
    public OrderReturnListDto getOrderReturnList(String keyword, EOrderCancelStatusFlag status, Pageable pageable){
        OrderReturnInfoAdminProjectionResponse page = orderCancelRepository.findAllOrderReturnByKeyword(keyword, status, pageable);
        List<OrderReturnDto> result = OrderReturnDto.of(page.getPage().getContent());
        return OrderReturnListDto.of(result, page.getPage(), page.getAllCount(), page.getReceiptCount(), page.getRefuseCount(), page.getCollectCount(), page.getCollectCompleteCount(), page.getCompleteCount());
    }


    @Transactional(readOnly = true)
    public List<OrderShippingDto> downloadOrderShipping(String keyword, EOrderShippingStatusFlag status){
        return OrderShippingDto.of(orderShippingRepository.findAllByKeyword(keyword, status), null);
    }

    @Transactional(readOnly = true)
    public List<OrderCancelDto> downloadOrderCancels(String keyword, EOrderCancelStatusFlag status){
        return OrderCancelDto.of(orderCancelRepository.findAllOrderCancelByKeyword(keyword, status));
    }

    @Transactional(readOnly = true)
    public List<OrderChangeDto> downloadOrderChanges(String keyword, EOrderCancelStatusFlag status){
        return OrderChangeDto.of(orderCancelRepository.findAllOrderChangeByKeyword(keyword, status));
    }

    @Transactional(readOnly = true)
    public List<OrderReturnDto> downloadOrderReturns(String keyword, EOrderCancelStatusFlag status){
        return OrderReturnDto.of(orderCancelRepository.findAllOrderReturnByKeyword(keyword, status));
    }


    @Transactional(readOnly = true)
    public OrderShippingDto getOrderShipping(Long id, Boolean isCancel){
        OrderShipping orderShipping = orderShippingRepository.findAdminOrderShippingById(id);
        List<CodeDto> codeList = CodeDto.of(codeRepository.findAllByGroupByIsHide(ECodeGroup.CARRIER, false));
        if(!isCancel){
            return OrderShippingDto.of(orderShipping, codeList, null);
        }
        UserAccount userAccount = null;
        String refundBank = null;
        String refundName = null;
        String refundAccount = null;
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdFetch(orderShipping.getOrderItems().get(0).getId());
        if(orderItem.getOrder().getPaymentType().equals("BANK")){
            userAccount = userAccountRepository.findByUserId(orderShipping.getOrder().getUserId());
            if(userAccount==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "환불 계좌 등록후 취소 요청이 가능합니다.");
            }
            refundName = userAccount.getBankHolderName();
            refundBank = userAccount.getBankName();
            refundAccount = userAccount.getBankAccountNumber();
        }

        OrderCancelItemTempDto orderCancelItemTempDto = OrderCancelItemTempDto.of(orderItem, EOrderCancelReasonFlag.CANCEL_REASON_101, refundName, refundBank, refundAccount);
        return OrderShippingDto.of(orderShipping, codeList, orderCancelItemTempDto);
    }

    @Transactional
    public void updateOrderShipping(OrderShippingDto request){
        OrderShipping orderShipping = orderShippingRepository.findByIdNotFetch(request.getId());
        orderShipping.updateCarrier(request.getCarrier(), request.getTrackingNumber());
    }

    @Transactional
    public void updateOrderShippingStatus(Long id, EOrderShippingStatusFlag status){
        OrderShipping orderShipping = orderShippingRepository.findByIdFetchOrder(id);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 구매 확정이된 주문입니다.");
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 취소처리가 접수된 주문입니다.");
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 취소처리가 완료된 주문입니다.");
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 반품처리가 접수된 주문입니다.");
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 반품처리가 완료된 주문입니다.");
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 교환처리가 접수된 주문입니다.");
        } else if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 교환처리가 완료된 주문입니다.");
        }
        orderShipping.updateAdminStatus(status);
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
            CarrierTrackResponse trackResponse = thirdPartyService.getCarrierTrackInfo(orderShipping.getCarrier(), orderShipping.getTrackingNumber());
            if(trackResponse!=null){
                if(trackResponse.getState().getId().equals("delivered")){
                    if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_READY)){
                        if(!orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
                            orderShipping.updateShippingDeliveryComplete(LocalDateTimeUtils.stringToLocalDateTime(trackResponse.getTo().getTime()));
                            OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderShipping.getOrder().getUserId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShipping.getStatus());
                            orderShippingLogRepository.save(orderShippingLog);
                        }
                    }
                }
            }
        } else {
            OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderShipping.getOrder().getUserId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShipping.getStatus());
            orderShippingLogRepository.save(orderShippingLog);
        }
    }

    @Transactional
    public void updateOrderCancelStatus(Long id, EOrderCancelStatusFlag status){
        OrderCancel orderCancel = orderCancelRepository.findByCancelIdFetchJoin(id);
        if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 취소처리가 완료된 주문입니다.");
        } else if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CANCEL_DENIED)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "취소가 거절된 주문입니다.");
        }
        OrderShipping orderShipping = orderCancel.getOrderShipping();
        User user = orderCancel.getOrderCancelComment().getUser();
        orderCancel.updateAdminCancelStatus(status);
        if(status.equals(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)){
            TossPaymentCancelResponse response = thirdPartyService.cancelPayment(orderCancel.getOrder().getPaymentKey(), TossPaymentRequest.PaymentCancel.of(orderCancel.getReason().getDescription(), orderCancel.getTotalAmount()));
            PurchaseCancel purchase = PurchaseCancel.newInstance(response.getOrderId(), response.getPaymentKey(), response.getTotalAmount(), AdminUtil.toJsonStr(response));
            purchaseCancelRepository.save(purchase);

            List<OrderCancelItem> cancelItemList = orderCancel.getOrderCancelItems();
            for(OrderCancelItem orderCancelItem: cancelItemList){
                String itemNumber = null;
                if(orderCancelItem.getType().equals(EOrderCancelItemFlag.ORDER_ITEM)){
                    Item item = orderCancelItem.getItem();
                    itemNumber = item.getItemNumber();
                } else if(orderCancelItem.getType().equals(EOrderCancelItemFlag.ORDER_ITEM_OPTION)){
                    ItemOption itemOption = orderCancelItem.getItemOption();
                    itemNumber = itemOption.getItemNumber();
                }
                ItemStock itemStock = itemStockRepository.findByItemNumber(itemNumber);
                if(itemStock!=null){
                    itemStock.updateOrderCancel(orderCancelItem.getQty());
                }
            }
            orderShipping.updateCancelComplete();
            OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_BACK_OFFICE, EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE);
            orderShippingLogRepository.save(orderShippingLog);
        } else if(status.equals(EOrderCancelStatusFlag.ORDER_CANCEL_DENIED)){
            OrderShippingLog orderShippingLog = orderShippingLogRepository.findLastShippingStatus(orderCancel.getOrderShipping().getId(), orderCancel.getOrderCancelComment().getUser().getId());
            if(orderShippingLog==null){
                throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "배송 로그가 존재하지 않습니다.");
            }

            switch (orderShippingLog.getStatus()){
                case ORDER_SHIPPING_CHECK:
                    orderShipping.updateShippingCheck();
                    break;
                case ORDER_SHIPPING_READY:
                    orderShipping.updateShippingReady();
                    break;
                case ORDER_SHIPPING_DELIVERY:
                    orderShipping.updateShippingDelivery();
                    break;
                case ORDER_SHIPPING_DELIVERY_COMPLETE:
                    orderShipping.updateShippingDeliveryComplete();
                    break;
                default:
                    throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, String.format("%s log status 오류", orderShippingLog.getStatus()));
            }
            OrderShippingLog save = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShippingLog.getStatus());
            orderShippingLogRepository.save(save);
        }
    }

    @Transactional
    public void updateOrderReturnStatus(Long id, EOrderCancelStatusFlag status){
        OrderCancel orderCancel = orderCancelRepository.findByCancelIdFetchJoin(id);
        if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_RETURN_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 반품완료된 주문입니다.");
        } else if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_RETURN_DENIED)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "반품 처리가 거절/철회된 주문입니다.");
        }
        OrderShipping orderShipping = orderCancel.getOrderShipping();
        User user = orderCancel.getOrderCancelComment().getUser();
        orderCancel.updateAdminReturnStatus(status);
        if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_RETURN_COMPLETE)){
            TossPaymentCancelResponse response = thirdPartyService.cancelPayment(orderCancel.getOrder().getPaymentKey(), TossPaymentRequest.PaymentCancel.of(orderCancel.getReason().getDescription(), orderCancel.getTotalAmount()));
            PurchaseCancel purchase = PurchaseCancel.newInstance(response.getOrderId(), response.getPaymentKey(), response.getTotalAmount(), AdminUtil.toJsonStr(response));
            purchaseCancelRepository.save(purchase);

            List<OrderCancelItem> cancelItemList = orderCancel.getOrderCancelItems();
            for(OrderCancelItem orderCancelItem: cancelItemList){
                String itemNumber = null;
                if(orderCancelItem.getType().equals(EOrderCancelItemFlag.ORDER_ITEM)){
                    Item item = orderCancelItem.getItem();
                    itemNumber = item.getItemNumber();
                } else if(orderCancelItem.getType().equals(EOrderCancelItemFlag.ORDER_ITEM_OPTION)){
                    ItemOption itemOption = orderCancelItem.getItemOption();
                    itemNumber = itemOption.getItemNumber();
                }
                ItemStock itemStock = itemStockRepository.findByItemNumber(itemNumber);
                if(itemStock!=null){
                    itemStock.updateOrderCancel(orderCancelItem.getQty());
                }
            }
            orderShipping.updateReturnComplete();
            OrderShippingLog save = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShipping.getStatus());
            orderShippingLogRepository.save(save);
        } else if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_RETURN_DENIED)){
            orderShipping.updateShippingDeliveryComplete();
            OrderShippingLog save = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShipping.getStatus());
            orderShippingLogRepository.save(save);
        }
    }

    @Transactional
    public void updateOrderChangeStatus(Long id, EOrderCancelStatusFlag status){
        OrderCancel orderCancel = orderCancelRepository.findByCancelIdFetchJoin(id);
        if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CHANGE_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 교환이 완료된 주문입니다.");
        } else if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CHANGE_DENIED)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "교환 처리가 거절/철회된 주문입니다.");
        }
        OrderShipping orderShipping = orderCancel.getOrderShipping();
        User user = orderCancel.getOrderCancelComment().getUser();
        orderCancel.updateAdminChangeStatus(status);
        if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CHANGE_COMPLETE)) {
            orderShipping.updateChangeComplete();
            OrderShippingLog save = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShipping.getStatus());
            orderShippingLogRepository.save(save);
        } else if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CHANGE_DENIED)){
            orderShipping.updateShippingDeliveryComplete();
            OrderShippingLog save = OrderShippingLog.newInstance(orderShipping.getId(), user.getId(), EGofieldService.GOFIELD_BACK_OFFICE, orderShipping.getStatus());
            orderShippingLogRepository.save(save);
        }
    }

    @Transactional
    public void updateOrderShippingCancel(OrderShippingDto request){
        OrderShipping orderShipping = orderShippingRepository.findByIdFetchOrder(request.getId());
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdFetch(orderShipping.getOrderItems().get(0).getId());
        User user = userRepository.findByUserId(orderShipping.getOrder().getUserId());
        String refundName = null;
        String refundAccount = null;
        String refundBank = null;
        if(orderItem.getOrder().getPaymentType().equals("BANK")){
            UserAccount userAccount = userAccountRepository.findByUserId(orderShipping.getOrder().getUserId());
            refundName = userAccount.getBankHolderName();
            refundBank = userAccount.getBankName();
            refundAccount = userAccount.getBankAccountNumber();
        }
        OrderShippingAddress orderShippingAddress = orderItem.getOrder().getShippingAddress();
        OrderCancelItemTempDto orderItemInfo = OrderCancelItemTempDto.of(orderItem, request.getReason(), refundName, refundAccount, refundBank);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, orderShippingAddress.getName(), orderShippingAddress.getTel(), orderShippingAddress.getZipCode(), orderShippingAddress.getAddress(), orderShippingAddress.getAddressExtra(), request.getReason().getDescription());
        orderCancelCommentRepository.save(orderCancelComment);

        OrderCancel orderCancel = OrderCancel.newCancelCompleteInstance(orderItem.getOrder(), orderItem.getOrderShipping(), orderCancelComment, orderItem.getItem().getShippingTemplate(), makeOrderCancelNumber(), EOrderCancelCodeFlag.USER, request.getReason(), orderItemInfo.getTotalAmount(), orderItemInfo.getItemPrice(), orderItemInfo.getDeliveryPrice(), orderItemInfo.getDiscountPrice(),  orderItemInfo.getTotalAmount(), orderItemInfo.getRefundPrice(),  orderItemInfo.getRefundName(), orderItemInfo.getRefundAccount(), orderItemInfo.getRefundBank());
        Item item = orderItemInfo.getIsOption() ? null : itemRepository.findByItemId(orderItemInfo.getItemId());
        ItemOption itemOption = orderItemInfo.getIsOption() ? itemOptionRepository.findByOptionId(orderItemInfo.getItemOptionId()) : null;
        EOrderCancelItemFlag itemType = orderItemInfo.getIsOption() ? EOrderCancelItemFlag.ORDER_ITEM_OPTION : EOrderCancelItemFlag.ORDER_ITEM;
        OrderCancelItem orderCancelItem = OrderCancelItem.newInstance(orderCancel, item, itemOption, orderItemInfo.getName(), orderItemInfo.getOptionName()==null ? null : AdminUtil.toJsonStr(orderItemInfo.getOptionName()), itemType, orderItemInfo.getQty(), orderItemInfo.getItemPrice());

        String itemNumber = null;
        if(itemType.equals(EOrderCancelItemFlag.ORDER_ITEM)){
            itemNumber = item.getItemNumber();
        } else if(orderCancelItem.getType().equals(EOrderCancelItemFlag.ORDER_ITEM_OPTION)){
            itemNumber = itemOption.getItemNumber();
        }
        ItemStock itemStock = itemStockRepository.findByItemNumber(itemNumber);
        if(itemStock!=null){
            itemStock.updateOrderCancel(orderCancelItem.getQty());
        }
        TossPaymentRequest.PaymentCancel paymentCancel = TossPaymentRequest.PaymentCancel.builder()
                .cancelAmount(orderCancel.getTotalAmount())
                .cancelReason(request.getReason().getDescription())
                .build();

        TossPaymentCancelResponse response = thirdPartyService.cancelPayment(orderShipping.getOrder().getPaymentKey(), paymentCancel);

        PurchaseCancel purchase = PurchaseCancel.newInstance(response.getOrderId(), response.getPaymentKey(), response.getTotalAmount(), AdminUtil.toJsonStr(response));
        purchaseCancelRepository.save(purchase);
        orderCancel.addOrderCancelItem(orderCancelItem);
        orderCancelRepository.save(orderCancel);
        if(orderItem.getStatus().equals(EOrderItemStatusFlag.ORDER_ITEM_RECEIPT)){
            orderItem.updateReceiptCancel();
        } else if(orderItem.getStatus().equals(EOrderItemStatusFlag.ORDER_ITEM_APPROVE)){
            orderItem.updateApproveCancel();
        }
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderShipping.getOrder().getUserId(), EGofieldService.GOFIELD_BACK_OFFICE, EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE);
        orderShippingLogRepository.save(orderShippingLog);
        orderItem.getOrderShipping().updateCancelComplete();
    }
}
