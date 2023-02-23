package com.gofield.api.service.order.dto.response;


import com.gofield.domain.rds.domain.order.EOrderStatusFlag;
import com.gofield.domain.rds.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private EOrderStatusFlag status;
    private LocalDateTime createDate;
    private LocalDateTime cancelDate;
    private LocalDateTime confirmDate;
    private LocalDateTime finishDate;
    private List<OrderShippingResponse> orderShippingList;

    @Builder
    private OrderResponse(Long id, String orderNumber, EOrderStatusFlag status, LocalDateTime createDate, LocalDateTime cancelDate, LocalDateTime confirmDate, LocalDateTime finishDate,  List<OrderShippingResponse> orderShippingList){
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status;
        this.createDate = createDate;
        this.cancelDate = cancelDate;
        this.confirmDate = confirmDate;
        this.finishDate = finishDate;
        this.orderShippingList = orderShippingList;
    }

    public static OrderResponse of(Order order, List<OrderShippingResponse> orderShippingList){
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus())
                .createDate(order.getCreateDate())
                .cancelDate(order.getCancelDate())
                .confirmDate(order.getConfirmDate())
                .finishDate(order.getFinishDate())
                .orderShippingList(orderShippingList)
                .build();
    }

    public static List<OrderResponse> of(List<Order> list){
        return list.stream()
                .map(p -> OrderResponse.of(p, OrderShippingResponse.of(p.getOrderShippings())))
                .collect(Collectors.toList());
    }


}
