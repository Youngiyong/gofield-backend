package com.gofield.api.dto.res;


import com.gofield.domain.rds.domain.order.EOrderStatusFlag;
import com.gofield.domain.rds.domain.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private String orderNumber;
    private int totalPrice;
    private int totalDiscount;
    private int totalDelivery;
    private String paymentCompany;
    private EOrderStatusFlag status;
    private LocalDateTime createDate;
    private LocalDateTime cancelDate;
    private LocalDateTime confirmDate;
    private LocalDateTime finishDate;
    private String tel;
    private String name;
    private String zipCode;
    private String address;
    private String addressExtra;
    private String shippingComment;
    private List<OrderShippingResponse> orderShippingList;

    @Builder
    private OrderDetailResponse(Long id, String orderNumber, int totalPrice, int totalDiscount , int totalDelivery,
                                String paymentCompany, EOrderStatusFlag status, LocalDateTime createDate, LocalDateTime cancelDate,
                                LocalDateTime confirmDate, LocalDateTime finishDate, String tel, String name,
                                String zipCode, String address, String addressExtra, String shippingComment, List<OrderShippingResponse> orderShippingList){
        this.id = id;
        this.orderNumber = orderNumber;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.totalDelivery = totalDelivery;
        this.paymentCompany = paymentCompany;
        this.status = status;
        this.createDate = createDate;
        this.cancelDate = cancelDate;
        this.confirmDate = confirmDate;
        this.finishDate = finishDate;
        this.tel = tel;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.shippingComment = shippingComment;
        this.orderShippingList = orderShippingList;
    }

    public static OrderDetailResponse of(Order order, List<OrderShippingResponse> orderShippingList){
        return OrderDetailResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalPrice(order.getTotalPrice())
                .totalDiscount(order.getTotalDiscount())
                .totalDelivery(order.getTotalDelivery())
                .paymentCompany(order.getPaymentCompany())
                .status(order.getStatus())
                .createDate(order.getCreateDate())
                .cancelDate(order.getCancelDate())
                .confirmDate(order.getConfirmDate())
                .finishDate(order.getFinishDate())
                .tel(order.getShippingAddress().getTel())
                .name(order.getShippingAddress().getName())
                .zipCode(order.getShippingAddress().getZipCode())
                .address(order.getShippingAddress().getAddress())
                .addressExtra(order.getShippingAddress().getAddressExtra())
                .shippingComment(order.getShippingAddress().getComment())
                .orderShippingList(orderShippingList)
                .build();
    }
}
