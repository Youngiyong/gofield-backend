
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.seller.Seller;
import com.gofield.domain.rds.domain.user.User;
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
@Table(	name = "order")
public class Order extends BaseTimeEntity {

    @Column
    private Long userId;

    @Column
    private Long sellerId;

    @Column(length = 64, nullable = false)
    private String orderNumber;

    @Column
    private String paymentKey;

    @Column
    private int totalItem;

    @Column
    private int totalDelivery;

    @Column
    private int totalPrice;

    @Column(nullable = false)
    private EOrderStatusFlag status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderShipping> orderShippings = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

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
    private Order(Long userId, Long sellerId, String orderNumber,  String paymentKey, int totalItem, int totalDelivery, int totalPrice, EOrderStatusFlag status){
        this.userId = userId;
        this.sellerId = sellerId;
        this.orderNumber = orderNumber;
        this.paymentKey = paymentKey;
        this.totalItem = totalItem;
        this.totalDelivery = totalDelivery;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public static Order newInstance(Long userId, Long sellerId, String orderNumber,  String paymentKey,
                                    int totalItem, int totalDelivery, int totalPrice, EOrderStatusFlag status){
        return Order.builder()
                .userId(userId)
                .sellerId(sellerId)
                .orderNumber(orderNumber)
                .paymentKey(paymentKey)
                .totalItem(totalItem)
                .totalDelivery(totalDelivery)
                .totalPrice(totalPrice)
                .status(status)
                .build();
    }

    public void addOrderShipping(OrderShipping orderShipping){
        this.orderShippings.add(orderShipping);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
    }
}
