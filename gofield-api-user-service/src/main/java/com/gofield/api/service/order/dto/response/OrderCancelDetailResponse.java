package com.gofield.api.service.order.dto.response;

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


@Getter
@NoArgsConstructor
public class OrderCancelDetailResponse {
    private Long id;
    private Long orderId;
    private String orderNumber;
    private String cancelNumber;
    private EOrderCancelTypeFlag type;
    private EOrderCancelStatusFlag status;
    private EOrderCancelReasonFlag reason;
    private String content;
    private String comment;
    private String paymentCompany;
    private String paymentType;
    private String cardNumber;
    private String cardType;
    private int installmentPlanMonth;
    private int totalAmount;
    private int totalItem;
    private int totalDelivery;
    private int totalDiscount;
    private int totalRefund;
    private int totalPg;
    private int totalSafeCharge;
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
    private OrderCancelDetailResponse(Long id, Long orderId, String orderNumber, String cancelNumber, EOrderCancelTypeFlag type, EOrderCancelStatusFlag status, EOrderCancelReasonFlag reason, String content, String comment, String paymentCompany, String paymentType,
                                      String cardNumber, String cardType, int installmentPlanMonth, int totalAmount, int totalItem, int totalDelivery, int totalDiscount, int totalRefund, int totalPg, int totalSafeCharge, String carrier, String trackingNumber,
                                      String refundName, String refundAccount, String refundBank, LocalDateTime createDate, LocalDateTime recalledDate, LocalDateTime refundDate,
                                      String receiver, String receiverTel, String receiverZipCode, String receiverAddress, String receiverAddressExtra, List<OrderCancelItemDetailResponse> cancelItems){
        this.id = id;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.cancelNumber = cancelNumber;
        this.type = type;
        this.status = status;
        this.reason = reason;
        this.content = content;
        this.comment = comment;
        this.paymentCompany = paymentCompany;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.installmentPlanMonth = installmentPlanMonth;
        this.totalAmount = totalAmount;
        this.totalItem = totalItem;
        this.totalDelivery = totalDelivery;
        this.totalDiscount = totalDiscount;
        this.totalRefund = totalRefund;
        this.totalSafeCharge = totalSafeCharge;
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
                .orderNumber(orderCancel.getOrder().getOrderNumber())
                .cancelNumber(orderCancel.getCancelNumber())
                .type(orderCancel.getType())
                .status(orderCancel.getStatus())
                .reason(orderCancel.getReason())
                .content(orderCancel.getOrderCancelComment().getContent())
                .paymentCompany(orderCancel.getOrder().getPaymentCompany())
                .paymentType(orderCancel.getOrder().getPaymentType())
                .cardNumber(orderCancel.getOrder().getCardNumber())
                .cardType(orderCancel.getOrder().getCardType())
                .installmentPlanMonth(orderCancel.getOrder().getInstallmentPlanMonth())
                .totalAmount(orderCancel.getTotalAmount())
                .totalItem(orderCancel.getTotalItem())
                .totalDelivery(orderCancel.getTotalDelivery())
                .totalDiscount(orderCancel.getTotalDiscount())
                .totalPg(orderCancel.getTotalPg())
                .totalSafeCharge(orderCancel.getTotalSafeCharge())
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
