package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class OrderSheetResponse {
    private ItemOrderSheetListResponse orderSheet;
    private UserAddressResponse shippingAddress;
    private Boolean isOrdered;

    @Builder
    private OrderSheetResponse(ItemOrderSheetListResponse orderSheet, UserAddressResponse shippingAddress, Boolean isOrdered){
        this.orderSheet = orderSheet;
        this.shippingAddress = shippingAddress;
        this.isOrdered = isOrdered;
    }

    public static OrderSheetResponse of(ItemOrderSheetListResponse orderSheet, UserAddressResponse shippingAddress, Boolean isOrdered){
        return OrderSheetResponse.builder()
                .orderSheet(orderSheet)
                .shippingAddress(shippingAddress)
                .isOrdered(isOrdered)
                .build();
    }
}
