package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.order.EOrderCancelTypeFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCancelListResponse {
    private List<OrderCancelResponse> list;

    @Builder
    private OrderCancelListResponse(List<OrderCancelResponse> list){
        this.list = list;
    }

    public static OrderCancelListResponse of(List<OrderCancelResponse> list){
        return OrderCancelListResponse.builder()
                .list(list)
                .build();

    }
}
