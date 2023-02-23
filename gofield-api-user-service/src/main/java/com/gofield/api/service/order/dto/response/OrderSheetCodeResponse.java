package com.gofield.api.service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderSheetCodeResponse {
    private String code;

    @Builder
    private OrderSheetCodeResponse(String code){
        this.code = code;
    }

    public static OrderSheetCodeResponse of(String code){
        return OrderSheetCodeResponse.builder()
                .code(code)
                .build();
    }
}
