package com.gofield.api.service.order.dto.response;

import com.gofield.domain.rds.domain.order.EPaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderWaitCreateResponse {
    private EPaymentType paymentType;
    private String nextUrl;
    private String accountNumber;

    private Boolean isOrdered;

    @Builder
    private OrderWaitCreateResponse(EPaymentType paymentType, String nextUrl, String accountNumber, Boolean isOrdered){
        this.paymentType = paymentType;
        this.nextUrl = nextUrl;
        this.accountNumber = accountNumber;
        this.isOrdered = isOrdered;
    }

    public static OrderWaitCreateResponse of(EPaymentType paymentType, String nextUrl, String accountNumber, Boolean isOrdered){
        return OrderWaitCreateResponse.builder()
                .paymentType(paymentType)
                .nextUrl(nextUrl)
                .accountNumber(accountNumber)
                .isOrdered(isOrdered)
                .build();
    }
}
