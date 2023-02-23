package com.gofield.api.service.order.dto.response;

import com.gofield.domain.rds.domain.order.OrderShippingAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderShippingAddressResponse {
    private Long id;
    private String tel;
    private String name;
    private String zipCode;
    private String address;
    private String addressExtra;
    private String shippingComment;

    @Builder
    private OrderShippingAddressResponse(Long id, String tel, String name, String zipCode, String address, String addressExtra, String shippingComment){
        this.id = id;
        this.tel = tel;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.shippingComment = shippingComment;
    }

    public static OrderShippingAddressResponse of(OrderShippingAddress orderShippingAddress){
        return OrderShippingAddressResponse.builder()
                .id(orderShippingAddress.getId())
                .tel(orderShippingAddress.getTel())
                .name(orderShippingAddress.getName())
                .zipCode(orderShippingAddress.getZipCode())
                .address(orderShippingAddress.getAddress())
                .addressExtra(orderShippingAddress.getAddressExtra())
                .shippingComment(orderShippingAddress.getComment())
                .build();
    }
}
