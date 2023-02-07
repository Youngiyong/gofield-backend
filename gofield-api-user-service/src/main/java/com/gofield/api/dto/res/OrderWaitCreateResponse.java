package com.gofield.api.dto.res;

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

    @Builder
    private OrderWaitCreateResponse(EPaymentType paymentType, String nextUrl, String accountNumber){
        this.paymentType = paymentType;
        this.nextUrl = nextUrl;
        this.accountNumber = accountNumber;
    }

    public static OrderWaitCreateResponse of(EPaymentType paymentType, String nextUrl, String accountNumber){
        return OrderWaitCreateResponse.builder()
                .paymentType(paymentType)
                .nextUrl(nextUrl)
                .accountNumber(accountNumber)
                .build();
    }
}
