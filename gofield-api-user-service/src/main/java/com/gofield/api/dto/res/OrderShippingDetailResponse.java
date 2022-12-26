package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import com.gofield.domain.rds.domain.order.OrderShipping;
import com.gofield.domain.rds.domain.order.OrderShippingAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderShippingDetailResponse {
    private Long id;
    private String shippingNumber;
    private EOrderShippingStatusFlag status;
    private String trackingNumber;
    private EItemChargeFlag chargeType;
    private int deliveryPrice;
    private String carrier;
    private LocalDateTime createDate;
    private LocalDateTime cancelDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime deliveredDate;
    private String tel;
    private String name;
    private String zipCode;
    private String address;
    private String addressExtra;
    private String shippingComment;
    private List<OrderItemResponse> orderItems;

    @Builder
    private OrderShippingDetailResponse(Long id, String shippingNumber, EOrderShippingStatusFlag status,
                                        String trackingNumber, EItemChargeFlag chargeType, int deliveryPrice, String carrier,
                                        LocalDateTime createDate, LocalDateTime cancelDate, LocalDateTime deliveryDate, LocalDateTime deliveredDate,
                                        String tel, String name, String zipCode, String address, String addressExtra, String shippingComment,
                                        List<OrderItemResponse> orderItems){
        this.id = id;
        this.shippingNumber = shippingNumber;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.chargeType = chargeType;
        this.deliveryPrice = deliveryPrice;
        this.carrier = carrier;
        this.createDate = createDate;
        this.cancelDate = cancelDate;
        this.deliveryDate = deliveryDate;
        this.deliveredDate = deliveredDate;
        this.tel = tel;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.shippingComment = shippingComment;
        this.orderItems = orderItems;
    }

    public static OrderShippingDetailResponse of(OrderShipping orderShipping, OrderShippingAddress orderShippingAddress){
        return OrderShippingDetailResponse.builder()
                .id(orderShipping.getId())
                .shippingNumber(orderShipping.getShippingNumber())
                .status(orderShipping.getStatus())
                .trackingNumber(orderShipping.getTrackingNumber())
                .chargeType(orderShipping.getChargeType())
                .deliveryPrice(orderShipping.getDeliveryPrice())
                .carrier(orderShipping.getCarrier())
                .createDate(orderShipping.getCreateDate())
                .cancelDate(orderShipping.getCancelDate())
                .deliveryDate(orderShipping.getDeliveryDate())
                .deliveredDate(orderShipping.getDeliveredDate())
                .tel(orderShippingAddress.getTel())
                .name(orderShippingAddress.getName())
                .zipCode(orderShippingAddress.getZipCode())
                .address(orderShippingAddress.getAddress())
                .addressExtra(orderShippingAddress.getAddressExtra())
                .shippingComment(orderShippingAddress.getComment())
                .orderItems(OrderItemResponse.of(orderShipping.getOrderItems()))
                .build();
    }

    public static OrderShippingDetailResponse of(Long id, String shippingNumber, EOrderShippingStatusFlag status,
                                                 String trackingNumber, EItemChargeFlag chargeType, int deliveryPrice, String carrier,
                                                 LocalDateTime createDate, LocalDateTime cancelDate, LocalDateTime deliveryDate, LocalDateTime deliveredDate,
                                                 String tel, String name, String zipCode, String address, String addressExtra, String shippingComment,
                                                 List<OrderItem> orderItems){
        return OrderShippingDetailResponse.builder()
                .id(id)
                .shippingNumber(shippingNumber)
                .status(status)
                .trackingNumber(trackingNumber)
                .chargeType(chargeType)
                .deliveryPrice(deliveryPrice)
                .carrier(carrier)
                .createDate(createDate)
                .cancelDate(cancelDate)
                .deliveryDate(deliveryDate)
                .deliveredDate(deliveredDate)
                .tel(tel)
                .name(name)
                .zipCode(zipCode)
                .address(address)
                .addressExtra(addressExtra)
                .shippingComment(shippingComment)
                .orderItems(OrderItemResponse.of(orderItems))
                .build();
    }

}
