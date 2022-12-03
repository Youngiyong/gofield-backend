package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderSheetResponse {
    private ItemOrderSheetListResponse orderSheet;
    private UserAddressResponse shippingAddress;

    @Builder
    private OrderSheetResponse(ItemOrderSheetListResponse orderSheet, UserAddressResponse shippingAddress){
        this.orderSheet = orderSheet;
        this.shippingAddress = shippingAddress;
    }

    public static OrderSheetResponse of(ItemOrderSheetListResponse orderSheet, UserAddressResponse shippingAddress){
        return OrderSheetResponse.builder()
                .orderSheet(orderSheet)
                .shippingAddress(shippingAddress)
                .build();
    }
}
