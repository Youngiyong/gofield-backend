
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.Item;
import com.gofield.domain.rds.domain.seller.Seller;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping  orderShipping;

    @Column
    private Long sellerId;

    @Column
    private Long itemId;

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
    private OrderItem(Order order, Long sellerId, Long itemId, OrderShipping orderShipping, String orderNumber,
                      String itemNumber, String name, int qty, int price){
        this.order = order;
        this.sellerId = sellerId;
        this.itemId = itemId;
        this.orderShipping = orderShipping;
        this.orderNumber = orderNumber;
        this.itemNumber = itemNumber;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.status = EOrderItemStatusFlag.ORDER_ITEM_RECEIPT;
    }

    public static OrderItem newInstance(Order order, Long sellerId, Long itemId, OrderShipping orderShipping, String orderNumber,
                                        String itemNumber, String name, int qty, int price){
        return OrderItem.builder()
                .order(order)
                .sellerId(sellerId)
                .itemId(itemId)
                .orderShipping(orderShipping)
                .orderNumber(orderNumber)
                .itemNumber(itemNumber)
                .name(name)
                .qty(qty)
                .price(price)
                .build();
    }
}
