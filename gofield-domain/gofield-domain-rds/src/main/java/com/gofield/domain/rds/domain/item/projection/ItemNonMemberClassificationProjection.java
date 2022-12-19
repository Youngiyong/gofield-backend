package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemNonMemberClassificationProjection {

    private final Long id;
    private final String itemNumber;
    private final String name;
    private final String brandName;
    private final String thumbnail;
    private final int price;

    private final int deliveryPrice;
    private final EItemClassificationFlag classification;
    private final EItemDeliveryFlag delivery;
    private final EItemGenderFlag gender;
    private final String tags;

    @QueryProjection
    public ItemNonMemberClassificationProjection(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, int deliveryPrice, EItemClassificationFlag classification, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.classification = classification;
        this.delivery = delivery;
        this.gender = gender;
        this.tags = tags;
    }
}
