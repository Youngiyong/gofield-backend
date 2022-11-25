
package com.gofield.domain.rds.entity.orderCancelItem;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.entity.order.Order;
import com.gofield.domain.rds.entity.orderCancel.OrderCancel;
import com.gofield.domain.rds.entity.seller.Seller;
import com.gofield.domain.rds.enums.order.EOrderCancelItemFlag;
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
