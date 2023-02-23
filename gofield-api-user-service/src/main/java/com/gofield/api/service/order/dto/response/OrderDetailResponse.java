package com.gofield.api.service.order.dto.response;

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
    private int totalItem;
    private int totalAmount;
    private int totalDiscount;
    private int totalDelivery;
    private String paymentCompany;

    private String bankName;

    private String virtualAccountNumber;
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
    private OrderDetailResponse(Long id, String orderNumber, int totalItem, int totalAmount, int totalDiscount , int totalDelivery,
                                String paymentCompany, String bankName, String virtualAccountNumber, EOrderStatusFlag status, LocalDateTime createDate, LocalDateTime cancelDate,
                                LocalDateTime confirmDate, LocalDateTime finishDate, String tel, String name,
                                String zipCode, String address, String addressExtra, String shippingComment, List<OrderShippingResponse> orderShippingList){
        this.id = id;
        this.orderNumber = orderNumber;
        this.totalItem = totalItem;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.totalDelivery = totalDelivery;
        this.paymentCompany = paymentCompany;
        this.bankName = bankName;
        this.virtualAccountNumber = virtualAccountNumber;
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
                .totalItem(order.getTotalItem())
                .totalAmount(order.getTotalAmount())
                .totalDiscount(order.getTotalDiscount())
                .totalDelivery(order.getTotalDelivery())
                .paymentCompany(order.getPaymentCompany())
                .bankName(order.getBankName())
                .virtualAccountNumber(order.getVirtualAccountNumber())
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
