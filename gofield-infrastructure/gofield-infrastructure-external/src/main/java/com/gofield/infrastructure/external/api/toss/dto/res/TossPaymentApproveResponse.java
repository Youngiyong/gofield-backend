package com.gofield.infrastructure.external.api.toss.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentApproveResponse {
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int taxExemptionAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private String useEscrow;
    private Boolean cultureExpense;
    private Card card;
    private String virtualAccount;
    private String transfer;
    private String mobilePhone;
    private String giftCertificate;
    private String cashReceipt;
    private String cancels;
    private String secret;
    private String type;
    private EasyPay easyPay;
    private String country;
    private String failure;
    private Boolean isPartialCancelable;
    private Url receipt;
    private Url checkout;
    private String currency;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;
    private String method;
    private String version;

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Url {
        private String url;
    }


    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EasyPay {
        private String provider;
        private int amount;
        private int discountAmount;
    }

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Card {
        private int amount;
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private String approveNo;
        private Boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private Boolean isInterestFree;
        private String interestPayer;
    }


}

