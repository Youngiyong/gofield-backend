package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemNonMemberProjection {

    private final Long id;
    private final String name;
    private final String brandName;
    private final Long sellerId;
    private final String thumbnail;
    private final String itemNumber;
    private final int price;
    private final int qty;
    private final EItemClassificationFlag classification;
    private final EItemSpecFlag spec;
    private final EItemDeliveryFlag delivery;
    private final EItemGenderFlag gender;
    private final String tags;
    private final String option;

    @QueryProjection
    public ItemNonMemberProjection(Long id, String name, String brandName, Long sellerId, String thumbnail, String itemNumber, int price, int qty, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.sellerId = sellerId;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.tags = tags;
        this.option = option;
    }
}
