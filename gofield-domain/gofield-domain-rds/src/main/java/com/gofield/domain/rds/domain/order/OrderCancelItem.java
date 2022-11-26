
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
@Table(	name = "order_cancel_item")
public class OrderCancelItem extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_id")
    private OrderCancel orderCancel;

    @Column
    private EOrderCancelItemFlag item;

    @Column
    private Long refId;

    @Column
    private int qty;

    @Column
    private int price;

    @Column
    private EOrderCancelReasonFlag reason;
}
