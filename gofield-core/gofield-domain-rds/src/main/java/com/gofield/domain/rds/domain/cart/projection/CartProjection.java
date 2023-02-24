package com.gofield.domain.rds.domain.cart.projection;

import com.gofield.domain.rds.domain.item.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CartProjection {

    private final Long id;
    private final String itemName;
    private final String itemNumber;
    private final Long sellerId;
    private final String sellerName;
    private final String optionName;
    private final String thumbnail;
    private final int price;
    private final int deliveryPrice;
    private final int qty;
    private final Boolean isOrder;
    private final Boolean isPaid;
    private final EItemClassificationFlag classification;
    private final EItemDeliveryFlag delivery;
    private final EItemSpecFlag spec;
    private final EItemGenderFlag gender;
    private final Boolean isCondition;
    private final int condition;
    private final EItemChargeFlag chargeType;
    private final int charge;
    private final int feeJeju;
    private final int feeJejuBesides;

    @QueryProjection
    public CartProjection(Long id, String itemName, String itemNumber, Long sellerId, String sellerName, String optionName, String thumbnail, int price, int deliveryPrice, int qty, Boolean isOrder, Boolean isPaid, EItemClassificationFlag classification, EItemDeliveryFlag delivery, EItemSpecFlag spec, EItemGenderFlag gender, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge, int feeJeju, int feeJejuBesides){
        this.id = id;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.qty = qty;
        this.isOrder = isOrder;
        this.isPaid = isPaid;
        this.classification = classification;
        this.delivery = delivery;
        this.spec = spec;
        this.gender = gender;
        this.isCondition = isCondition;
        this.condition = condition;
        this.chargeType = chargeType;
        this.charge = charge;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
    }

}
