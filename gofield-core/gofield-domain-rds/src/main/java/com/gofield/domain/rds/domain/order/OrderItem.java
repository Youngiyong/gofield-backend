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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_item")
public class OrderItem extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping orderShipping;

    @Column
    private Long sellerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_option_id")
    private OrderItemOption orderItemOption;

    @Column(nullable = false, length = 16)
    private String orderNumber;

    @Column(nullable = false, length = 16)
    private String orderItemNumber;

    @Column
    private String itemNumber;

    @Column
    private String name;

    @Column
    private int qty;

    @Column
    private int price;

    @Column
    private Boolean isReview;

    @Column(name = "status_flag")
    private EOrderItemStatusFlag status;

    @Column
    private Double safeCommission;

    @Column
    private LocalDateTime reviewDate;

    @Builder
    private OrderItem(Order order, Long sellerId, Item item, OrderItemOption orderItemOption, OrderShipping orderShipping, String orderNumber, String orderItemNumber,
                      String itemNumber, String name, int qty, int price, Double safeCommission){
        this.order = order;
        this.sellerId = sellerId;
        this.item = item;
        this.orderItemOption = orderItemOption;
        this.orderShipping = orderShipping;
        this.orderNumber = orderNumber;
        this.orderItemNumber = orderItemNumber;
        this.itemNumber = itemNumber;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.safeCommission = safeCommission;
        this.status = EOrderItemStatusFlag.ORDER_ITEM_RECEIPT;
    }

    public static OrderItem newInstance(Order order, Long sellerId, Item item, OrderItemOption orderItemOption, OrderShipping orderShipping, String orderNumber, String orderItemNumber,
                                        String itemNumber, String name, int qty, int price, Double safeCommission){
        return OrderItem.builder()
                .order(order)
                .sellerId(sellerId)
                .item(item)
                .orderItemOption(orderItemOption)
                .orderShipping(orderShipping)
                .orderNumber(orderNumber)
                .orderItemNumber(orderItemNumber)
                .itemNumber(itemNumber)
                .name(name)
                .qty(qty)
                .price(price)
                .safeCommission(safeCommission)
                .build();
    }

    public void updateReview(){
        this.isReview = true;
        this.reviewDate = LocalDateTime.now();
    }


    public void updateReceiptCancel(){
        this.status = EOrderItemStatusFlag.ORDER_ITEM_RECEIPT_CANCEL;
    }

    public void updateApproveCancel(){
        this.status = EOrderItemStatusFlag.ORDER_ITEM_APPROVE_CANCEL;
    }

}
