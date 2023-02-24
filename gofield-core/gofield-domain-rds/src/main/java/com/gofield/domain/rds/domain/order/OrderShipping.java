package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_shipping")
public class OrderShipping extends BaseTimeEntity {

    @Column(name = "seller_id")
    private Long sellerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(length = 32, nullable = false)
    private String orderNumber;

    @Column(name = "shipping_number")
    private String shippingNumber;

    @Column(name = "shipping_comment")
    private String comment;

    @Column(name = "status_flag")
    private EOrderShippingStatusFlag status;

    @Column
    private String trackingNumber;

    @Column
    private EItemChargeFlag chargeType;

    @Column(name = "charge")
    private int charge;

    @Column
    private Boolean isPaid;

    @Column(name = "total_delivery")
    private int deliveryPrice;

    @Column(name = "condition_price")
    private int condition;

    @Column
    private int feeJeju;

    @Column
    private int feeJejuBesides;

    @Column
    private String carrier;

    @Column
    private LocalDateTime deliveryDate;

    @Column
    private LocalDateTime deliveredDate;

    @Column
    private LocalDateTime cancelDate;

    @Column
    private LocalDateTime finishDate;

    @Column
    private LocalDateTime finishedDate;

    @Column
    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "orderShipping", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    private OrderShipping(Long sellerId, Order order, String orderNumber, String shippingNumber, String comment,
                          EItemChargeFlag chargeType, Boolean isPaid, int charge, int deliveryPrice, int condition,
                          int feeJeju, int feeJejuBesides, EOrderShippingStatusFlag status){
        this.sellerId = sellerId;
        this.order = order;
        this.orderNumber = orderNumber;
        this.shippingNumber = shippingNumber;
        this.comment = comment;
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK;
        this.chargeType = chargeType;
        this.isPaid = isPaid;
        this.charge = charge;
        this.deliveryPrice = deliveryPrice;
        this.condition = condition;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
        this.status = status;
    }

    public static OrderShipping newInstance(Long sellerId, Order order, String orderNumber, String shippingNumber, String comment,
                                            EItemChargeFlag chargeType, Boolean isPaid, int charge, int deliveryPrice, int condition,
                                            int feeJeju, int feeJejuBesides, EOrderShippingStatusFlag status){
        return OrderShipping.builder()
                .sellerId(sellerId)
                .order(order)
                .orderNumber(orderNumber)
                .shippingNumber(shippingNumber)
                .comment(comment)
                .chargeType(chargeType)
                .isPaid(isPaid)
                .charge(charge)
                .deliveryPrice(deliveryPrice)
                .condition(condition)
                .feeJeju(feeJeju)
                .feeJejuBesides(feeJejuBesides)
                .status(status)
                .build();
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
    }

    public void updateDelete(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_DELETE;
        this.deleteDate = LocalDateTime.now();
    }

    public Boolean isShippingReview(){
        if(this.status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE) ||
                this.status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE)){
            return true;
        } else {
            return false;
        }
    }

    public void updateShippingDeliveryComplete(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE;
        this.deliveredDate = LocalDateTime.now();
    }

    public void updateShippingDeliveryComplete(LocalDateTime deliveryDate){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE;
        this.deliveryDate = deliveryDate;
        this.deliveredDate = LocalDateTime.now();
    }

    public void updateShippingComplete(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_COMPLETE;
        this.finishedDate = LocalDateTime.now();
    }

    public void updateChange() { this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE; }
    public void updateChangeComplete() { this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CHANGE_COMPLETE; }

    public void updateCancel(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL;
    }

    public void updateCancelComplete(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CANCEL_COMPLETE;
        this.cancelDate = LocalDateTime.now();
    }

    public void updateReturn() { this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN; }
    public void updateReturnComplete() { this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_RETURN_COMPLETE; }

    public void updateCarrier(String carrier, String trackingNumber){
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
    }

    public void updateAdminStatus(EOrderShippingStatusFlag status){
       if(status.equals(EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY_COMPLETE)){
            this.deliveredDate = LocalDateTime.now();
        }
        this.status = status;
    }

    public void updateShippingReady(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_READY;
    }

    public void updateShippingCheck(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK;
    }

    public void updateShippingDelivery(){
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_DELIVERY;
    }

    public void updateShippingDeposit() {
        this.status = EOrderShippingStatusFlag.ORDER_SHIPPING_CHECK;
    }
}
