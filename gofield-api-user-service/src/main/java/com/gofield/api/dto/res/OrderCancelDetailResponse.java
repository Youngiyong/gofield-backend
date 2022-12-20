package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.order.EOrderCancelReasonFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelStatusFlag;
import com.gofield.domain.rds.domain.order.EOrderCancelTypeFlag;
import com.gofield.domain.rds.domain.order.OrderCancel;
import com.gofield.domain.rds.domain.item.ShippingTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderCancelDetailResponse {
    private Long id;
    private Long orderId;
    private EOrderCancelTypeFlag type;
    private EOrderCancelStatusFlag status;
    private EOrderCancelReasonFlag reason;
    private String content;
    private String paymentType;
    private int totalAmount;
    private int totalItem;

    private int totalDelivery;

    private int totalDiscount;

    private int totalPg;

    private String carrier;

    private String trackingNumber;
    private String refundName;
    private String refundAccount;
    private String refundBank;
    private LocalDateTime createDate;
    private LocalDateTime recalledDate;
    private LocalDateTime refundDate;
    private String receiver;
    private String receiverTel;
    private String receiverZipCode;
    private String receiverAddress;
    private String receiverAddressExtra;
    private List<OrderCancelItemDetailResponse> cancelItems;

    @Builder
    private OrderCancelDetailResponse(Long id, Long orderId, EOrderCancelTypeFlag type, EOrderCancelStatusFlag status, EOrderCancelReasonFlag reason, String content,  String paymentType,
                                      int totalAmount, int totalItem, int totalDelivery, int totalDiscount, int totalPg, String carrier, String trackingNumber,
                                      String refundName, String refundAccount, String refundBank, LocalDateTime createDate, LocalDateTime recalledDate, LocalDateTime refundDate,
                                      String receiver, String receiverTel, String receiverZipCode, String receiverAddress, String receiverAddressExtra, List<OrderCancelItemDetailResponse> cancelItems){
        this.id = id;
        this.orderId = orderId;
        this.type = type;
        this.status = status;
        this.reason = reason;
        this.content = content;
        this.paymentType = paymentType;
        this.totalAmount = totalAmount;
        this.totalItem = totalItem;
        this.totalDelivery = totalDelivery;
        this.totalDiscount = totalDiscount;
        this.totalPg = totalPg;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.refundName = refundName;
        this.refundAccount = refundAccount;
        this.refundBank = refundBank;
        this.createDate = createDate;
        this.recalledDate = recalledDate;
        this.refundDate = refundDate;
        this.receiver = receiver;
        this.receiverTel = receiverTel;
        this.receiverZipCode = receiverZipCode;
        this.receiverAddress = receiverAddress;
        this.receiverAddressExtra = receiverAddressExtra;
        this.cancelItems = cancelItems;
    }

    public static OrderCancelDetailResponse of(OrderCancel orderCancel){
        ShippingTemplate shippingTemplate = orderCancel.getShippingTemplate();
        return OrderCancelDetailResponse.builder()
                .id(orderCancel.getId())
                .orderId(orderCancel.getOrder().getId())
                .type(orderCancel.getType())
                .status(orderCancel.getStatus())
                .reason(orderCancel.getReason())
                .content(orderCancel.getOrderCancelComment().getContent())
                .paymentType(orderCancel.getOrder().getPaymentType())
                .totalAmount(orderCancel.getTotalAmount())
                .totalItem(orderCancel.getTotalItem())
                .totalDelivery(orderCancel.getTotalDelivery())
                .totalDiscount(orderCancel.getTotalDiscount())
                .totalPg(orderCancel.getTotalPg())
                .carrier(orderCancel.getCarrier())
                .trackingNumber(orderCancel.getTrackingNumber())
                .refundName(orderCancel.getRefundName())
                .refundAccount(orderCancel.getRefundAccount())
                .refundBank(orderCancel.getRefundBank())
                .createDate(orderCancel.getCreateDate())
                .recalledDate(orderCancel.getRecalledDate())
                .refundDate(orderCancel.getRefundDate())
                .receiver(shippingTemplate==null ? null : shippingTemplate.getReceiver())
                .receiverTel(shippingTemplate==null ? null : shippingTemplate.getReceiverTel())
                .receiverZipCode(shippingTemplate==null ? null : shippingTemplate.getReturnZipCode())
                .receiverAddress(shippingTemplate==null ? null : shippingTemplate.getReturnAddress())
                .receiverAddressExtra(shippingTemplate==null ? null : shippingTemplate.getReturnAddressExtra())
                .cancelItems(OrderCancelItemDetailResponse.of(orderCancel.getOrderCancelItems()))
                .build();
    }

}
