package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemOrderSheetProjection {
    private final Long id;
    private final String brandName;
    private final String name;
    private final String optionName;
    private final Long sellerId;
    private final Long bundleId;
    private final Long optionId;
    private final String thumbnail;
    private final String itemNumber;
    private final int itemPrice;
    private final int optionPrice;
    private final EItemDeliveryFlag delivery;
    private final int deliveryPrice;
    private final EItemOptionTypeFlag optionType;
    private final int qty;
    private final Boolean isOption;
    private final EItemStatusFlag status;
    private final Boolean isCondition;
    private final int condition;
    private final EItemChargeFlag chargeType;
    private final int charge;
    private final int feeJeju;
    private final int feeJejuBesides;

    @QueryProjection
    public ItemOrderSheetProjection(Long id, String brandName, String name, String optionName, Long sellerId, Long bundleId, Long optionId, String thumbnail, String itemNumber, int itemPrice, int optionPrice, EItemDeliveryFlag delivery, int deliveryPrice, EItemOptionTypeFlag optionType, int qty, Boolean isOption, EItemStatusFlag status, Boolean isCondition, int condition, EItemChargeFlag chargeType, int charge, int feeJeju, int feeJejuBesides) {
        this.id = id;
        this.brandName = brandName;
        this.name = name;
        this.optionName = optionName;
        this.sellerId = sellerId;
        this.bundleId = bundleId;
        this.optionId = optionId;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.itemPrice = itemPrice;
        this.optionPrice = optionPrice;
        this.delivery = delivery;
        this.deliveryPrice = deliveryPrice;
        this.optionType = optionType;
        this.qty = qty;
        this.isOption = isOption;
        this.status = status;
        this.isCondition = isCondition;
        this.condition = condition;
        this.chargeType = chargeType;
        this.charge = charge;
        this.feeJeju = feeJeju;
        this.feeJejuBesides = feeJejuBesides;
    }
}
