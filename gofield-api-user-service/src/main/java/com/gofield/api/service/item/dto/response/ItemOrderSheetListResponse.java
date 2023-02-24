package com.gofield.api.service.item.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemOrderSheetListResponse {

    private int totalPrice;
    private int totalDelivery;
    private int totalSafeCharge;
    private int totalAmount;
    private List<ItemOrderSheetResponse> orderSheetList;


    @Builder
    private ItemOrderSheetListResponse(int totalPrice, int totalDelivery, int totalSafeCharge,  int totalAmount, List<ItemOrderSheetResponse> orderSheetList){
        this.totalPrice = totalPrice;
        this.totalDelivery = totalDelivery;
        this.totalSafeCharge = totalSafeCharge;
        this.totalAmount = totalAmount;
        this.orderSheetList = orderSheetList;
    }

    public static ItemOrderSheetListResponse of(int totalPrice, int totalDelivery, Double totalSafeCharge, List<ItemOrderSheetResponse> orderSheetList){
        return ItemOrderSheetListResponse.builder()
                .totalPrice(totalPrice)
                .totalDelivery(totalDelivery)
                .totalSafeCharge(totalSafeCharge.intValue())
                .totalAmount(totalPrice+totalDelivery+totalSafeCharge.intValue())
                .orderSheetList(orderSheetList)
                .build();
    }

}
