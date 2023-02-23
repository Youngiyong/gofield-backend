package com.gofield.infrastructure.external.api.toss.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentCancelResponse {
    private String mId;
    private String version;
    private String paymentKey;
    private String lastTransactionKey;
    private String orderId;
    private String orderName;
    private String method;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private String useEscrow;
    private Boolean cultureExpense;
    private Url checkout;
    private Card card;
    private Url receipt;
    private String virtualAccount;
    private String transfer;
    private String mobilePhone;
    private String giftCertificate;
    private String foreignEasyPay;
    private String cashReceipt;
    private String discount;
    private List<Cancel> cancels;
    private String secret;
    private String type;
    private Map<String, Object> easyPay;
    private String country;
    private String failure;
    private String currency;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;
    private int taxExemptionAmount;

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
    public static class Card {
        private String amount;
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private Boolean isInterestFree;
        private String interestPayer;
        private String approveNo;
        private Boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private String receiptUrl;
    }

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cancel {
        private String cancelReason;
        private String canceledAt;
        private int cancelAmount;
        private int taxFreeAmount;
        private int taxExemptionAmount;
        private int refundableAmount;
        private int easyPayDiscountAmount;
        private String transactionKey;
    }

}

