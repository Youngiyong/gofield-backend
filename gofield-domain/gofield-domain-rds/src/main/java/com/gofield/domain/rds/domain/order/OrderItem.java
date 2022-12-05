package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_item")
public class OrderItem extends BaseTimeEntity {

    @Column
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping  orderShipping;

    @Column
    private Long sellerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column
    private String orderNumber;

    @Column
    private String itemNumber;

    @Column
    private String name;

    @Column
    private int qty;

    @Column
    private int price;

    @Column(name = "status_flag")
    private EOrderItemStatusFlag status;

    @Builder
    private OrderItem(Long orderId, Long sellerId, Item item, OrderShipping orderShipping, String orderNumber,
                      String itemNumber, String name, int qty, int price){
        this.orderId = orderId;
        this.sellerId = sellerId;
        this.item = item;
        this.orderShipping = orderShipping;
        this.orderNumber = orderNumber;
        this.itemNumber = itemNumber;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.status = EOrderItemStatusFlag.ORDER_ITEM_RECEIPT;
    }

    public static OrderItem newInstance(Long orderId, Long sellerId, Item item, OrderShipping orderShipping, String orderNumber,
                                        String itemNumber, String name, int qty, int price){
        return OrderItem.builder()
                .orderId(orderId)
                .sellerId(sellerId)
                .item(item)
                .orderShipping(orderShipping)
                .orderNumber(orderNumber)
                .itemNumber(itemNumber)
                .name(name)
                .qty(qty)
                .price(price)
                .build();
    }
}
