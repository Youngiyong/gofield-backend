package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemOrderSheetListResponse {

    private int totalPrice;
    private int totalDelivery;
    private List<ItemOrderSheetResponse> orderSheetList;


    @Builder
    private ItemOrderSheetListResponse(int totalPrice, int totalDelivery, List<ItemOrderSheetResponse> orderSheetList){
        this.totalPrice = totalPrice;
        this.totalDelivery = totalDelivery;
        this.orderSheetList = orderSheetList;
    }

    public static ItemOrderSheetListResponse of(int totalPrice, int totalDelivery, List<ItemOrderSheetResponse> orderSheetList){
        return ItemOrderSheetListResponse.builder()
                .totalPrice(totalPrice)
                .totalDelivery(totalDelivery)
                .orderSheetList(orderSheetList)
                .build();
    }

}
