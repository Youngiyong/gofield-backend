package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
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

    @Column
    private Long orderId;

    @Column
    private Long itemId;

    @Column
    private String orderNumber;

    @Column
    private String itemNumber;

    @Column
    private EOrderCancelCodeFlag code;

    @Column
    private String name;

    @Column
    private int qty;

    @Column
    private int cancelQty;

    @Column
    private int price;

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
}
