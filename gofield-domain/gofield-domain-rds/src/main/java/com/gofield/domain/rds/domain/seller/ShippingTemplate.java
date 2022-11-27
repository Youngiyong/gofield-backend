
package com.gofield.domain.rds.domain.seller;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
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
@Table(	name = "shipping_template")
public class ShippingTemplate extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column
    private String title;

    @Column
    private Boolean isCondition;

    @Column
    private int condition;

    @Column
    private EItemChargeFlag chargeType;

    @Column
    private int charge;

    @Column
    private Boolean isPaid;

    @Column
    private String exchangeCourierName;

    @Column
    private int exchangeCharge;

    @Column
    private int takebackCharge;

    @Column
    private Boolean isFee;

    @Column
    private int feeJeju;

    @Column
    private int feeJejuBesides;

    @Column
    private String shippingComment;
}
