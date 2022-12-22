package com.gofield.admin.service;


import com.gofield.admin.dto.*;
import com.gofield.admin.util.AdminUtil;
import com.gofield.common.exception.InvalidException;
import com.gofield.common.model.ErrorAction;
import com.gofield.common.model.ErrorCode;
import com.gofield.domain.rds.domain.code.CodeRepository;
import com.gofield.domain.rds.domain.code.ECodeGroup;
import com.gofield.domain.rds.domain.common.EGofieldService;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.ItemOptionRepository;
import com.gofield.domain.rds.domain.item.ItemRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CodeRepository codeRepository;
    private final OrderCancelRepository orderCancelRepository;
    private final OrderShippingRepository orderShippingRepository;
    private final OrderShippingLogRepository orderShippingLogRepository;

    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final OrderCancelCommentRepository orderCancelCommentRepository;
    private final PurchaseCancelRepository purchaseCancelRepository;
    private final ThirdPartyService thirdPartyService;

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
        }
        orderShipping.updateAdminStatus(status);
        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderShipping.getOrder().getUserId(), EGofieldService.GOFIELD_BACK_OFFICE, status);
        orderShippingLogRepository.save(orderShippingLog);
    }

    @Transactional
    public void updateOrderCancelStatus(Long id, EOrderCancelStatusFlag status){
        OrderCancel orderCancel = orderCancelRepository.findByCancelIdFetchJoin(id);
        if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "이미 취소처리가 완료된 주문입니다.");
        } else if(orderCancel.getStatus().equals(EOrderCancelStatusFlag.ORDER_CANCEL_DENIED)){
            throw new InvalidException(ErrorCode.E400_INVALID_EXCEPTION, ErrorAction.TOAST, "취소가 거절된 주문입니다.");
        }
        if(status.equals(EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE)){
            Order order = orderCancel.getOrder();
            TossPaymentRequest.PaymentCancel paymentCancel = TossPaymentRequest.PaymentCancel.builder()
                    .cancelAmount(orderCancel.getTotalAmount())
                    .cancelReason(EOrderCancelReasonFlag.CHANGE_REASON_103.getDescription())
                    .build();
            TossPaymentCancelResponse response = thirdPartyService.cancelPayment(order.getPaymentKey(), paymentCancel);
            PurchaseCancel purchase = PurchaseCancel.newInstance(response.getOrderId(), response.getPaymentKey(), response.getTotalAmount(), AdminUtil.toJsonStr(response));
            purchaseCancelRepository.save(purchase);
        }
        orderCancel.updateAdminStatus(status);
    }

    @Transactional
    public void updateOrderShippingCancel(OrderShippingDto request){
        OrderShipping orderShipping = orderShippingRepository.findByIdFetchOrder(request.getId());
        orderShipping.updateAdminCancel();

        OrderShippingLog orderShippingLog = OrderShippingLog.newInstance(orderShipping.getId(), orderShipping.getOrder().getUserId(), EGofieldService.GOFIELD_BACK_OFFICE, EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE);
        orderShippingLogRepository.save(orderShippingLog);
        OrderItem orderItem =  orderItemRepository.findByOrderItemIdFetch(orderShipping.getOrderItems().get(0).getId());
        User user = userRepository.findByUserId(orderShipping.getOrder().getUserId());
        String refundName = null;
        String refundAccount = null;
        String refundBank = null;

        if(orderItem.getOrder().getPaymentType().equals("BANK")){
            UserAccount userAccount =userAccountRepository.findByUserId(orderShipping.getOrder().getUserId());
            refundName = userAccount.getBankHolderName();
            refundBank = userAccount.getBankName();
            refundAccount = userAccount.getBankAccountNumber();
        }

        OrderCancelItemTempDto orderItemInfo = OrderCancelItemTempDto.of(orderItem, request.getReason(), refundName, refundAccount, refundBank);
        OrderCancelComment orderCancelComment = OrderCancelComment.newInstance(user, request.getReason().getDescription());
        orderCancelCommentRepository.save(orderCancelComment);
        /*
        TODo: 반품비 제거, 할인율 제거 후 환불 처리 필요
         */
        OrderCancel orderCancel = OrderCancel.newCancelCompleteInstance(orderItem.getOrder(),orderCancelComment, orderItem.getItem().getShippingTemplate(), EOrderCancelStatusFlag.ORDER_CANCEL_COMPLETE, EOrderCancelCodeFlag.USER, request.getReason(), orderItemInfo.getItemPrice()-orderItemInfo.getDiscountPrice(), orderItemInfo.getItemPrice(), orderItemInfo.getDeliveryPrice(), orderItemInfo.getDiscountPrice(), 0,  orderItemInfo.getRefundName(), orderItemInfo.getRefundAccount(), orderItemInfo.getRefundBank());
        Item item = orderItemInfo.getIsOption() ? null : itemRepository.findByItemId(orderItemInfo.getItemId());
        ItemOption itemOption = orderItemInfo.getIsOption() ? itemOptionRepository.findByOptionId(orderItemInfo.getItemOptionId()) : null;
        EOrderCancelItemFlag itemType = orderItemInfo.getIsOption() ? EOrderCancelItemFlag.ORDER_ITEM_OPTION : EOrderCancelItemFlag.ORDER_ITEM;
        OrderCancelItem orderCancelItem = OrderCancelItem.newInstance(orderCancel, item, itemOption, orderItemInfo.getName(), orderItemInfo.getOptionName()==null ? null : AdminUtil.toJsonStr(orderItemInfo.getOptionName()), itemType, orderItemInfo.getQty(), orderItemInfo.getItemPrice());

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
        orderItem.getOrderShipping().updateCancel();
    }
}