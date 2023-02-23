package com.gofield.api.service.order.dto.response;

import com.gofield.domain.rds.domain.order.EPaymentType;
import com.gofield.domain.rds.domain.order.OrderSheet;
import com.gofield.domain.rds.domain.order.OrderWait;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateResponse {
    private String orderId;
    private String paymentKey;
    private OrderWait orderWait;
    private OrderSheet orderSheet;
    private String paymentCompany;
    private EPaymentType paymentType;
    private String cardNumber;
    private String cardType;
    private int installmentPlanMonth;

    @Builder
    private OrderCreateResponse(String orderId, String paymentKey, OrderWait orderWait, OrderSheet orderSheet, String paymentCompany, EPaymentType paymentType, String cardNumber, String cardType, int installmentPlanMonth){
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.orderWait = orderWait;
        this.orderSheet = orderSheet;
        this.paymentCompany = paymentCompany;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.installmentPlanMonth = installmentPlanMonth;
    }

    public static OrderCreateResponse of(String orderId, String paymentKey, OrderWait orderWait, OrderSheet orderSheet, String paymentCompany, EPaymentType paymentType, String cardNumber, String cardType, int installmentPlanMonth){
        return OrderCreateResponse.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderWait(orderWait)
                .orderSheet(orderSheet)
                .paymentCompany(paymentCompany)
                .paymentType(paymentType)
                .cardNumber(cardNumber)
                .cardType(cardType)
                .installmentPlanMonth(installmentPlanMonth)
                .build();
    }


    public static OrderCreateResponse of(String orderId, String paymentKey, OrderWait orderWait, OrderSheet orderSheet, EPaymentType paymentType){
        return OrderCreateResponse.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .orderWait(orderWait)
                .orderSheet(orderSheet)
                .paymentType(paymentType)
                .build();
    }
}
