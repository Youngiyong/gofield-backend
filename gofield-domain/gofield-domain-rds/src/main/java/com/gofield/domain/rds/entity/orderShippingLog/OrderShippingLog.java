
package com.gofield.domain.rds.entity.orderShippingLog;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.order.Order;
import com.gofield.domain.rds.entity.orderShipping.OrderShipping;
import com.gofield.domain.rds.entity.user.User;
import com.gofield.domain.rds.enums.EGofieldService;
import com.gofield.domain.rds.enums.order.EOrderCancelCodeFlag;
import com.gofield.domain.rds.enums.order.EOrderShippingStatusFlag;
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
@Table(	name = "order_shipping_log")
public class OrderShippingLog extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_shipping_id")
    private OrderShipping shipping;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private EGofieldService service;

    @Column
    private EOrderShippingStatusFlag status;
}
