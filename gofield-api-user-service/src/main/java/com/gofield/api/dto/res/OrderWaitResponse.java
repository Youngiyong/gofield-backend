package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.order.OrderWait;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderWaitResponse {
    private String nextUrl;

    @Builder
    private OrderWaitResponse(String nextUrl){
        this.nextUrl = nextUrl;
    }

    public static OrderWaitResponse of(String nextUrl){
        return OrderWaitResponse.builder()
                .nextUrl(nextUrl)
                .build();
    }
}
