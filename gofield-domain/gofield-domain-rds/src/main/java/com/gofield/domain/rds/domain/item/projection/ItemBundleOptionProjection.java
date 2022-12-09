package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemBundleOptionProjection {

    private final Long id;
    private final String name;
    private final String optionName;
    private final Long sellerId;
    private final Long bundleId;
    private final Long optionId;
    private final String thumbnail;
    private final String itemNumber;
    private final int price;
    private final int optionPrice;
    private final EItemOptionTypeFlag optionType;
    private final int qty;
    private final Boolean isOption;
    private final EItemStatusFlag status;

    @QueryProjection
    public ItemBundleOptionProjection(Long id, String name, String optionName, Long sellerId, Long bundleId, Long optionId, String thumbnail, String itemNumber, int price, int optionPrice, EItemOptionTypeFlag optionType, int qty, Boolean isOption, EItemStatusFlag status) {
        this.id = id;
        this.name = name;
        this.optionName = optionName;
        this.sellerId = sellerId;
        this.bundleId = bundleId;
        this.optionId = optionId;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.price = price;
        this.optionPrice = optionPrice;
        this.optionType = optionType;
        this.qty = qty;
        this.isOption = isOption;
        this.status = status;
    }
}
