package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.order.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderCancelResponse {
    private Long id;
    private Long orderId;

    private String cancelNumber;
    private EOrderCancelTypeFlag type;
    private EOrderCancelStatusFlag status;
    private EOrderCancelReasonFlag reason;
    private String comment;
    private String paymentType;
    private String refundName;
    private String refundAccount;
    private String refundBank;
    private LocalDateTime createDate;
    private LocalDateTime recalledDate;
    private LocalDateTime refundDate;


    private List<OrderCancelItemResponse> cancelItems;

    @Builder
    private OrderCancelResponse(Long id, Long orderId, String cancelNumber, EOrderCancelTypeFlag type, EOrderCancelStatusFlag status, EOrderCancelReasonFlag reason, String comment, String paymentType,
                                String refundName, String refundAccount, String refundBank, LocalDateTime createDate, LocalDateTime recalledDate, LocalDateTime refundDate, List<OrderCancelItemResponse> cancelItems){
        this.id = id;
        this.orderId = orderId;
        this.cancelNumber = cancelNumber;
        this.type = type;
        this.status = status;
        this.reason = reason;
        this.comment = comment;
        this.paymentType = paymentType;
        this.refundName = refundName;
        this.refundAccount = refundAccount;
        this.refundBank = refundBank;
        this.createDate = createDate;
        this.recalledDate = recalledDate;
        this.refundDate = refundDate;
        this.cancelItems = cancelItems;
    }

    public static OrderCancelResponse of(OrderCancel orderCancel){
        return OrderCancelResponse.builder()
                .id(orderCancel.getId())
                .orderId(orderCancel.getOrder().getId())
                .cancelNumber(orderCancel.getCancelNumber())
                .type(orderCancel.getType())
                .status(orderCancel.getStatus())
                .reason(orderCancel.getReason())
                .paymentType(orderCancel.getOrder().getPaymentType())
                .refundName(orderCancel.getRefundName())
                .refundAccount(orderCancel.getRefundAccount())
                .refundBank(orderCancel.getRefundBank())
                .createDate(orderCancel.getCreateDate())
                .recalledDate(orderCancel.getRecalledDate())
                .refundDate(orderCancel.getRefundDate())
                .cancelItems(OrderCancelItemResponse.of(orderCancel.getOrderCancelItems()))
                .build();
    }

    public static List<OrderCancelResponse> of(List<OrderCancel> list){
        return list.stream().map(p -> OrderCancelResponse.of(p)).collect(Collectors.toList());
    }
}
