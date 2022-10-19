
package com.gofield.domain.rds.entity.orderShipping;


import com.gofield.domain.rds.entity.BaseTimeEntity;
import com.gofield.domain.rds.entity.order.Order;
import com.gofield.domain.rds.entity.seller.Seller;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(	name = "order_shipping")
public class OrderShipping extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(length = 20, nullable = false)
    private String orderNumber;

}
