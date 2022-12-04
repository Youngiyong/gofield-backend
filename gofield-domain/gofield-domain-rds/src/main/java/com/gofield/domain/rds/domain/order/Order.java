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
    @Column
    private Long userId;

    @Column(length = 64, nullable = false)
    private String orderNumber;

    @Column
    private String paymentKey;

    @Column
    private int totalDelivery;

    @Column
    private int totalPrice;

    @Column
    private int totalDiscount;

    @Column(nullable = false, name = "status_flag")
    private EOrderStatusFlag status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderShipping> orderShippings = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

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
    private Order(Long userId, String orderNumber,  String paymentKey, int totalDelivery, int totalPrice, int totalDiscount,  EOrderStatusFlag status){
        this.userId = userId;
        this.orderNumber = orderNumber;
        this.paymentKey = paymentKey;
        this.totalDelivery = totalDelivery;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public static Order newInstance(Long userId,  String orderNumber,  String paymentKey,
                                    int totalDelivery, int totalPrice, int totalDiscount){
        return Order.builder()
                .userId(userId)
                .orderNumber(orderNumber)
                .paymentKey(paymentKey)
                .totalDelivery(totalDelivery)
                .totalPrice(totalPrice)
                .totalDiscount(totalDiscount)
                .status(EOrderStatusFlag.ORDER_CREATE)
                .build();
    }

    public void addOrderShipping(OrderShipping orderShipping){
        this.orderShippings.add(orderShipping);
    }

}
