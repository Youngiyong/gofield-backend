package com.gofield.api.service.order.dto.response;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCancelListResponse {
    private List<OrderCancelResponse> list;
    private PaginationResponse page;

    @Builder
    private OrderCancelListResponse(List<OrderCancelResponse> list, PaginationResponse page){
        this.list = list;
        this.page = page;
    }

    public static OrderCancelListResponse of(List<OrderCancelResponse> list, PaginationResponse page){
        return OrderCancelListResponse.builder()
                .list(list)
                .page(page)
                .build();

    }
}
