package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.order.EOrderShippingStatusFlag;
import com.gofield.domain.rds.domain.order.OrderItem;
import com.gofield.domain.rds.domain.order.OrderShipping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderShippingResponse {
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
    private List<OrderItemResponse> orderItems;

    @Builder
    private OrderShippingResponse(Long id, String shippingNumber, EOrderShippingStatusFlag status,
                                  String trackingNumber, EItemChargeFlag chargeType, int deliveryPrice, String carrier,
                                  LocalDateTime createDate, LocalDateTime cancelDate,  LocalDateTime deliveryDate, LocalDateTime deliveredDate, List<OrderItemResponse> orderItems){
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
        this.orderItems = orderItems;
    }

    public static OrderShippingResponse of(Long id, String shippingNumber, EOrderShippingStatusFlag status,
                                           String trackingNumber, EItemChargeFlag chargeType, int deliveryPrice, String carrier,
                                           LocalDateTime createDate, LocalDateTime cancelDate, LocalDateTime deliveryDate, LocalDateTime deliveredDate, List<OrderItem> orderItems){
        return OrderShippingResponse.builder()
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
                .orderItems(OrderItemResponse.of(orderItems))
                .build();
    }

    public static List<OrderShippingResponse> of(List<OrderShipping> list){
        return list
                .stream()
                .map(p ->
                            OrderShippingResponse.of(p.getId(), p.getShippingNumber(), p.getStatus(),
                                    p.getTrackingNumber(), p.getChargeType(), p.getDeliveryPrice(), p.getCarrier(), p.getCreateDate(), p.getCancelDate(),
                                    p.getDeliveryDate(), p.getDeliveredDate(), p.getOrderItems()))
                .collect(Collectors.toList());
    }
}
