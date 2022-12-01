package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class OrderSheetResponse {
    private List<Map<String, Object>> orderItemList;
    private UserAddressResponse shippingAddress;
    private List<CodeResponse> shippingCodeList;

    @Builder
    private OrderSheetResponse(List<Map<String, Object>> orderItemList, UserAddressResponse shippingAddress, List<CodeResponse> shippingCodeList){
        this.orderItemList = orderItemList;
        this.shippingAddress = shippingAddress;
        this.shippingCodeList = shippingCodeList;
    }

    public static OrderSheetResponse of(List<Map<String, Object>> orderItemList, UserAddressResponse shippingAddress, List<CodeResponse> shippingCodeList){
        return OrderSheetResponse.builder()
                .orderItemList(orderItemList)
                .shippingAddress(shippingAddress)
                .shippingCodeList(shippingCodeList)
                .build();
    }
}
