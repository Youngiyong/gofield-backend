package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "orders")
public class Order extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private OrderShippingAddress shippingAddress;

    @Column
    private Long userId;

    @Column(length = 64, nullable = false)
    private String orderNumber;

    @Column
    private String paymentKey;

    @Column
    private int totalItem;

    @Column
    private int totalAmount;

    @Column
    private int totalDelivery;
    @Column
    private int totalDiscount;

    @Column
    private String paymentCompany;

    @Column
    private String paymentType;

    @Column
    private String cardNumber;

    @Column
    private String cardType;

    @Column
    private int installmentPlanMonth;

    @Column(nullable = false, name = "status_flag")
    private EOrderStatusFlag status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderShipping> orderShippings = new ArrayList<>();

    @Column
    private LocalDateTime deleteDate;

    @Column
    private LocalDateTime cancelDate;

    @Column
    private LocalDateTime checkDate;

    @Column
    private LocalDateTime confirmDate;

    @Column
    private LocalDateTime purchaseDate;

    @Column
    private LocalDateTime finishDate;

    @Builder
    private Order(OrderShippingAddress shippingAddress, Long userId, String orderNumber,  String paymentKey, int totalItem, int totalAmount, int totalDelivery, int totalPrice, int totalDiscount,  String paymentCompany, String paymentType, String cardNumber, String cardType, int installmentPlanMonth, EOrderStatusFlag status){
        this.shippingAddress = shippingAddress;
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.paymentKey = paymentKey;
        this.totalItem = totalItem;
        this.totalAmount = totalAmount;
        this.totalDelivery = totalDelivery;
        this.totalDiscount = totalDiscount;
        this.paymentCompany = paymentCompany;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.installmentPlanMonth = installmentPlanMonth;
        this.status = status;
    }

    public static Order newInstance(OrderShippingAddress shippingAddress, Long userId,  String orderNumber,  String paymentKey,
                                    int totalItem, int totalAmount, int totalDelivery, int totalDiscount, String paymentCompany, String paymentType, String cardNumber, String cardType, int installmentPlanMonth){
        return Order.builder()
                .shippingAddress(shippingAddress)
                .userId(userId)
                .orderNumber(orderNumber)
                .paymentKey(paymentKey)
                .totalDelivery(totalDelivery)
                .totalItem(totalItem)
                .totalAmount(totalAmount)
                .totalDiscount(totalDiscount)
                .paymentCompany(paymentCompany)
                .paymentType(paymentType)
                .cardNumber(cardNumber)
                .cardType(cardType)
                .installmentPlanMonth(installmentPlanMonth)
                .status(EOrderStatusFlag.ORDER_CREATE)
                .build();
    }

    public void addOrderShipping(OrderShipping orderShipping){
        this.orderShippings.add(orderShipping);
    }


    public void updateDelete(){
        this.status = EOrderStatusFlag.ORDER_DELETE;
        this.deleteDate = LocalDateTime.now();
    }
}
