
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
@Table(	name = "order_cancel_item")
public class OrderCancelItem extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_id")
    private OrderCancel cancel;

    @Column(nullable = false)
    private Long refId;

    @Column(name = "type_flag")
    private EOrderCancelItemFlag type;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private int price;

    @Column(name = "reason_flag")
    private EOrderCancelReasonFlag reason;

    @Builder
    private OrderCancelItem(OrderCancel orderCancel, Long refId, EOrderCancelItemFlag type, int qty, int price, EOrderCancelReasonFlag reason){
        this.cancel = orderCancel;
        this.refId = refId;
        this.type = type;
        this.qty = qty;
        this.price = price;
        this.reason = reason;
    }

    public static OrderCancelItem newInstance(OrderCancel orderCancel, Long refId, EOrderCancelItemFlag type, int qty, int price, EOrderCancelReasonFlag reason){
        return OrderCancelItem.builder()
                .orderCancel(orderCancel)
                .refId(refId)
                .type(type)
                .qty(qty)
                .price(price)
                .reason(reason)
                .build();
    }
}
