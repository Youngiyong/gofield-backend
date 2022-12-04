package com.gofield.domain.rds.domain.order;

import com.gofield.domain.rds.domain.common.BaseTimeEntity;
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
    private EOrderCancelReasonFlag reason;

    @Column
    private int totalPrice;

    @Column
    private int totalDelivery;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    private OrderCancel(Long orderId, Long itemId, String orderNumber, String itemNumber, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalPrice, int totalDelivery, String content) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.orderNumber = orderNumber;
        this.itemNumber = itemNumber;
        this.code = code;
        this.reason = reason;
        this.totalPrice = totalPrice;
        this.totalDelivery = totalDelivery;
        this.content = content;
    }

    public static OrderCancel newInstance(Long orderId, Long itemId, String orderNumber, String itemNumber, EOrderCancelCodeFlag code, EOrderCancelReasonFlag reason, int totalPrice, int totalDelivery, String content) {
        return OrderCancel.builder()
                .orderId(orderId)
                .itemId(itemId)
                .orderNumber(orderNumber)
                .itemNumber(itemNumber)
                .code(code)
                .reason(reason)
                .totalPrice(totalPrice)
                .totalDelivery(totalDelivery)
                .content(content)
                .build();
    }
}
