package com.gofield.api.service.cart.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartCountResponse {
    private int totalCount;

    @Builder
    private CartCountResponse(int totalCount){
        this.totalCount = totalCount;
    }

    public static CartCountResponse of(int totalCount){
        return CartCountResponse.builder()
                .totalCount(totalCount)
                .build();
    }
}
