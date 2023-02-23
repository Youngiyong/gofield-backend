package com.gofield.api.service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderListResponse {
    private List<OrderResponse> list;

    @Builder
    private OrderListResponse(List<OrderResponse> list){
        this.list = list;
    }

    public static OrderListResponse of(List<OrderResponse> list){
        return OrderListResponse.builder()
                .list(list)
                .build();
    }
}
