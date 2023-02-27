package com.gofield.api.service.order.dto.response;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.order.*;
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
    private Long cancelId;
    private String reason;
    private String comment;

    @Builder
    private OrderShippingResponse(Long id, String shippingNumber, EOrderShippingStatusFlag status,
                                  String trackingNumber, EItemChargeFlag chargeType, int deliveryPrice, String carrier,
                                  LocalDateTime createDate, LocalDateTime cancelDate,  LocalDateTime deliveryDate, LocalDateTime deliveredDate, List<OrderItemResponse> orderItems, Long cancelId, String reason, String comment){
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
        this.cancelId = cancelId;
        this.reason = reason;
        this.comment = comment;
    }

    public static OrderShippingResponse of(OrderShipping orderShipping, OrderCancelRepository orderCancelRepository){
        String comment = null;
        String reason = null;
        Long cancelId = null;
        if(orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN) || orderShipping.getStatus().equals(EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE)){
            OrderCancel orderCancel = orderCancelRepository.findByShippingIdAndStatusReturn(orderShipping.getId());
            comment = orderCancel.getOrderCancelComment().getContent();
            reason = orderCancel.getReason().getDescription();
            cancelId = orderCancel.getId();
        }
        return OrderShippingResponse.builder()
                .id(orderShipping.getId())
                .cancelId(cancelId)
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
                .orderItems(OrderItemResponse.of(orderShipping.getOrderItems()))
                .reason(reason)
                .comment(comment)
                .build();
    }


    public static OrderShippingResponse of(OrderShipping orderShipping){
        return OrderShippingResponse.builder()
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
                .orderItems(OrderItemResponse.of(orderShipping.getOrderItems()))
                .build();
    }

    public static List<OrderShippingResponse> of(List<OrderShipping> list, OrderCancelRepository orderCancelRepository){
        return list
                .stream()
                .map(p->OrderShippingResponse.of(p, orderCancelRepository))
                .collect(Collectors.toList());
    }

    public static List<OrderShippingResponse> of(List<OrderShipping> list){
        return list
                .stream()
                .map(OrderShippingResponse::of)
                .collect(Collectors.toList());
    }
}
