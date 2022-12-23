
package com.gofield.domain.rds.domain.item;


import com.gofield.domain.rds.domain.common.BaseTimeEntity;
import com.gofield.domain.rds.domain.item.EItemChargeFlag;
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
@Table(	name = "shipping_template")
public class ShippingTemplate extends BaseTimeEntity {

    @Column
    private Long sellerId;

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

    @Column
    private String zipCode;

    @Column
    private String address;

    @Column
    private String addressExtra;

    @Column
    private String receiver;

    @Column
    private String receiverTel;

    @Column
    private String returnZipCode;

    @Column
    private String returnAddress;

    @Column
    private String returnAddressExtra;


//    @Builder
//    private ShippingTemplate(Long sellerId, String title, Boolean isCondition)
}
