package com.gofield.infrastructure.external.api.toss.dto.req;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;


public class TossPaymentRequest {

    @Builder
    @Getter
    public static class PaymentVirtual {
        private int amount;
        private String bank;
        private String orderId;
        private String orderName;
        private String customerName;
        private String customerEmail;
        private String customerMobilePhone;
        private String dueDate;

        public static PaymentVirtual of(int amount, String bank, String orderId, String orderName, String customerName, String customerEmail, String customerMobilePhone, String dueDate){
            return PaymentVirtual.builder()
                    .amount(amount)
                    .bank(bank)
                    .orderId(orderId)
                    .orderName(orderName)
                    .customerName(customerName)
                    .customerEmail(customerEmail)
                    .customerMobilePhone(customerMobilePhone)
                    .dueDate(dueDate)
                    .build();
        }
    }

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

    @Builder
    @Getter
    public static class PaymentCancel {
        @NotNull(message = "취소사유는 필수값입니다.")
        private String cancelReason;
        private Integer cancelAmount;
        /*
        ToDo: 가상계좌 추가시 추가 https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%B7%A8%EC%86%8C
         */

        public static PaymentCancel of(String cancelReason, Integer cancelAmount){
            return PaymentCancel.builder()
                    .cancelReason(cancelReason)
                    .cancelAmount(cancelAmount)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class PaymentVirtualCallback {
        private String createdAt;
        private String secret;
        private String status;
        private String transactionKey;
        private String orderId;
    }

}
