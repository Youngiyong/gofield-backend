package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class OrderTempResponse {
    private List<Map<String, Object>> orderItemList;
    private UserAddressResponse shippingAddress;
    private List<CodeResponse> shippingCodeList;

    @Builder
    private OrderTempResponse(List<Map<String, Object>> orderItemList, UserAddressResponse shippingAddress, List<CodeResponse> shippingCodeList){
        this.orderItemList = orderItemList;
        this.shippingAddress = shippingAddress;
        this.shippingCodeList = shippingCodeList;
    }

    public static OrderTempResponse of(List<Map<String, Object>> orderItemList, UserAddressResponse shippingAddress, List<CodeResponse> shippingCodeList){
        return OrderTempResponse.builder()
                .orderItemList(orderItemList)
                .shippingAddress(shippingAddress)
                .shippingCodeList(shippingCodeList)
                .build();
    }
}
