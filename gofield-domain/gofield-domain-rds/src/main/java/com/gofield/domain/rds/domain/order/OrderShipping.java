
package com.gofield.domain.rds.domain.order;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.seller.Seller;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
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
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private String orderNumber;

    @Column
    private String comment;

    @Column
    private String commentExtra;

    @Column
    private String shippingNumber;

    @Column
    private EOrderShippingStatusFlag status;

    @Column
    private String trackingNumber;

    @Column
    private EItemChargeFlag chargeType;

    @Column
    private int charge;

    @Column
    private int totalDelivery;

    @Column
    private int condition;

    @Column
    private int feeJeju;

    @Column
    private int feeJejuBesides;

    @Column
    private String companyCode;

    @Column
    private LocalDateTime deliveryDate;

    @Column
    private LocalDateTime deliveredDate;

    @Column
    private LocalDateTime finishDate;

    @Column
    private LocalDateTime finishedDate;
}