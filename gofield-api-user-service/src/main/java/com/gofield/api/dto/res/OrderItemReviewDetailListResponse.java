package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderItemReviewDetailListResponse {
    private List<OrderItemReviewDetailResponse> list;

    @Builder
    private OrderItemReviewDetailListResponse(List<OrderItemReviewDetailResponse> list){
        this.list = list;
    }

    public static OrderItemReviewDetailListResponse of(List<OrderItemReviewDetailResponse> list){
        return OrderItemReviewDetailListResponse.builder()
                .list(list)
                .build();
    }
}
