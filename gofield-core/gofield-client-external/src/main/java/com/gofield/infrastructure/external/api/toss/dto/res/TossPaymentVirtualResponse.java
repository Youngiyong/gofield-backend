package com.gofield.infrastructure.external.api.toss.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentVirtualResponse {
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private String useEscrow;
    private Boolean cultureExpense;
    private String card;
    private Account virtualAccount;
    private String transfer;
    private String mobilePhone;
    private String giftCertificate;
    private String cashReceipt;
    private String discount;
    private String cancels;
    private String secret;
    private String type;
    private String easyPay;
    private String country;
    private String failure;
    private Boolean isPartialCancelable;
    private Url receipt;
    private Url checkout;
    private String currency;
    private int taxExemptionAmount;
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
    public static class Account {
        private String accountNumber;
        private String accountType;
        private String bankCode;
        private String customerName;
        private String dueDate;
        private Boolean expired;
        private String settlementStatus;
        private String refundStatus;
    }
}

