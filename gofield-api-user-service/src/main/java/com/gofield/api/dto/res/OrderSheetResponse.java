package com.gofield.api.dto.res;

import com.gofield.api.dto.req.OrderRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderSheetResponse {
    private OrderRequest.OrderSheet orderSheet;
    private UserAddressResponse shippingAddress;
    private List<CodeResponse> shippingCodeList;

    @Builder
    private OrderSheetResponse(OrderRequest.OrderSheet orderSheet, UserAddressResponse shippingAddress, List<CodeResponse> shippingCodeList){
        this.orderSheet = orderSheet;
        this.shippingAddress = shippingAddress;
        this.shippingCodeList = shippingCodeList;
    }

    public static OrderSheetResponse of(OrderRequest.OrderSheet orderSheet, UserAddressResponse shippingAddress, List<CodeResponse> shippingCodeList){
        return OrderSheetResponse.builder()
                .orderSheet(orderSheet)
                .shippingAddress(shippingAddress)
                .shippingCodeList(shippingCodeList)
                .build();
    }
}
