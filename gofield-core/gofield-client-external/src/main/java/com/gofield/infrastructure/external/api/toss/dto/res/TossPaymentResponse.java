package com.gofield.infrastructure.external.api.toss.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TossPaymentResponse {
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
    private String card;
    private String type;
    private Boolean isPartialCancelable;
    private String currency;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;
    private String method;
    private String version;
    private Checkout checkout;

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Checkout {
        private String url;
    }

}

