
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

    @Column(name = "conditional")
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


    @Builder
    private ShippingTemplate(Long sellerId, int condition, String title, Boolean isCondition, EItemChargeFlag chargeType, int charge, Boolean isPaid,
                             String exchangeCourierName, int exchangeCharge, int takebackCharge, Boolean isFee, int feeJeju, int feeJejuBesides,
                             String shippingComment, String zipCode, String address, String addressExtra, String receiver, String receiverTel,
                             String returnZipCode, String returnAddress, String returnAddressExtra){
        this.sellerId = sellerId;
        this.condition = condition;
        this.title = title;
        this.isCondition = isCondition;
        this.chargeType = chargeType;
        this.charge = charge;
        this.isPaid = isPaid;
        this.exchangeCourierName = exchangeCourierName;
        this.exchangeCharge = exchangeCharge;
        this.takebackCharge = takebackCharge;
        this.isFee = isFee;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
        this.shippingComment = shippingComment;
        this.zipCode = zipCode;
        this.address = address;
        this.addressExtra = addressExtra;
        this.receiver = receiver;
        this.receiverTel = receiverTel;
        this.returnZipCode = returnZipCode;
        this.returnAddress = returnAddress;
        this.returnAddressExtra = returnAddressExtra;
    }

    public static ShippingTemplate newInstance(Long sellerId, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge, Boolean isPaid, int exchangeCharge, int takebackCharge, Boolean isFee, int feeJeju, int feeJejuBesides,
                                                String zipCode, String address, String addressExtra, String returnZipCode, String returnAddress, String returnAddressExtra){
        return ShippingTemplate.builder()
                .sellerId(sellerId)
                .isCondition(isCondition)
                .condition(condition)
                .chargeType(chargeType)
                .charge(charge)
                .isPaid(isPaid)
                .exchangeCharge(exchangeCharge)
                .takebackCharge(takebackCharge)
                .isFee(isFee)
                .feeJeju(feeJeju)
                .feeJejuBesides(feeJejuBesides)
                .zipCode(zipCode)
                .address(address)
                .addressExtra(addressExtra)
                .returnZipCode(returnZipCode)
                .returnAddress(returnAddress)
                .returnAddressExtra(returnAddressExtra)
                .build();
    }

    public static ShippingTemplate newInstance(Long sellerId, String title, Boolean isCondition, EItemChargeFlag chargeType, int charge, Boolean isPaid,
                                               String exchangeCourierName, int exchangeCharge, int takebackCharge, Boolean isFee, int feeJeju, int feeJejuBesides,
                                               String shippingComment, String zipCode, String address, String addressExtra, String receiver, String receiverTel,
                                               String returnZipCode, String returnAddress, String returnAddressExtra){
        return ShippingTemplate.builder()
                .sellerId(sellerId)
                .title(title)
                .isCondition(isCondition)
                .chargeType(chargeType)
                .charge(charge)
                .isPaid(isPaid)
                .exchangeCourierName(exchangeCourierName)
                .exchangeCharge(exchangeCharge)
                .takebackCharge(takebackCharge)
                .isFee(isFee)
                .feeJeju(feeJeju)
                .feeJejuBesides(feeJejuBesides)
                .shippingComment(shippingComment)
                .zipCode(zipCode)
                .address(address)
                .addressExtra(addressExtra)
                .receiver(receiver)
                .receiverTel(receiverTel)
                .returnZipCode(returnZipCode)
                .returnAddress(returnAddress)
                .returnAddressExtra(returnAddressExtra)
                .build();
    }
}
