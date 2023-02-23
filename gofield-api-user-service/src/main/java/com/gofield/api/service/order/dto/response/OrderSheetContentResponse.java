package com.gofield.api.service.order.dto.response;


import com.gofield.api.service.item.dto.response.ItemOrderSheetListResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderSheetContentResponse {
    private String orderName;
    private ItemOrderSheetListResponse orderSheetList;
    private List<Long> cartIdList;

    @Builder
    private OrderSheetContentResponse(String orderName, ItemOrderSheetListResponse orderSheetList, List<Long> cartIdList){
        this.orderName = orderName;
        this.orderSheetList = orderSheetList;
        this.cartIdList = cartIdList;
    }

    public static OrderSheetContentResponse of(String orderName, ItemOrderSheetListResponse orderSheetList, List<Long> cartIdList){
        return OrderSheetContentResponse.builder()
                .orderName(orderName)
                .orderSheetList(orderSheetList)
                .cartIdList(cartIdList)
                .build();
    }
}
