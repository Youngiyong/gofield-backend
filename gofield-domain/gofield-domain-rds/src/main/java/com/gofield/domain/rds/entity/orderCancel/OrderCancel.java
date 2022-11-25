
package com.gofield.domain.rds.entity.orderCancel;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.order.Order;
import com.gofield.domain.rds.entity.seller.Seller;
import com.gofield.domain.rds.enums.order.EOrderCancelCodeFlag;
import com.gofield.domain.rds.enums.order.EOrderCancelReasonFlag;
import lombok.AccessLevel;
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
@Table(	name = "order_cancel")
public class OrderCancel extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private EOrderCancelCodeFlag code;

    @Column
    private int amount;

    @Column
    private int totalItem;

    @Column
    private int totalDelivery;

    @Column
    private EOrderCancelReasonFlag reason;

    @Column
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
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
    private int cancelQty;

    @Column
    private int price;
}
