package com.gofield.api.service.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderItemReviewListResponse {
    private List<OrderItemReviewResponse> list;

    @Builder
    private OrderItemReviewListResponse(List<OrderItemReviewResponse> list){
        this.list = list;
    }

    public static OrderItemReviewListResponse of(List<OrderItemReviewResponse> list){
        return OrderItemReviewListResponse.builder()
                .list(list)
                .build();
    }
}
