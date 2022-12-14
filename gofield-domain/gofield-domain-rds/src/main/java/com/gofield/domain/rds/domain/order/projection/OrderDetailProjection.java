package com.gofield.domain.rds.domain.order.projection;

import com.gofield.domain.rds.domain.item.EItemChargeFlag;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OrderDetailProjection {

    private final Long id;
    private final String itemName;
    private final String itemNumber;
    private final Long sellerId;
    private final String sellerName;
    private final String optionName;
    private final String thumbnail;

    private final int price;
    private final int qty;
    private final Boolean isOrder;
    private final EItemClassificationFlag classification;
    private final EItemSpecFlag spec;
    private final EItemGenderFlag gender;
    private final Boolean isCondition;
    private final int condition;
    private final EItemChargeFlag chargeType;
    private final int charge;
    private final int feeJeju;
    private final int feeJejuBesides;

    @QueryProjection
    public OrderDetailProjection(Long id, String itemName, String itemNumber, Long sellerId, String sellerName, String optionName, String thumbnail, int price, int qty, Boolean isOrder, EItemClassificationFlag classification, EItemSpecFlag spec, EItemGenderFlag gender, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge, int feeJeju, int feeJejuBesides){
        this.id = id;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.qty = qty;
        this.isOrder = isOrder;
        this.classification = classification;
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
