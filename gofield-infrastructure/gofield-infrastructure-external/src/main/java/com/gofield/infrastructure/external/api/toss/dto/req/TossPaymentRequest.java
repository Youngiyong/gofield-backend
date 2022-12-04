package com.gofield.infrastructure.external.api.toss.dto.req;

import lombok.Builder;
import lombok.Getter;


public class TossPaymentRequest {

    @Builder
    @Getter
    public static class Payment{
        private int amount;
        private String method;
        private String orderId;
        private String orderName;
        private String cardCompany;
        private String easyPay;
        private String successUrl;
        private String failUrl;

        public static Payment of(int amount, String method, String orderId, String orderName, String cardCompany, String easyPay, String successUrl, String failUrl){
            return Payment.builder()
                    .amount(amount)
                    .method(method)
                    .orderId(orderId)
                    .orderName(orderName)
                    .cardCompany(cardCompany)
                    .easyPay(easyPay)
                    .successUrl(successUrl)
                    .failUrl(failUrl)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class PaymentApprove {
        private int amount;
        private String orderId;
        private String paymentKey;

        public static PaymentApprove of(int amount, String orderId, String paymentKey){
            return PaymentApprove.builder()
                    .amount(amount)
                    .orderId(orderId)
                    .paymentKey(paymentKey)
                    .build();
        }
    }


}
