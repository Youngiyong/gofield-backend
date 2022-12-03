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

    @Column(name = "code_flag")
    private EOrderCancelCodeFlag code;

    @Column(name = "reason_flag")
    private EOrderCancelReasonFlag reason_flag;

    @Column
    private int totalPrice;

    @Column
    private int totalDelivery;

    @Column(columnDefinition = "TEXT")
    private String content;
}
