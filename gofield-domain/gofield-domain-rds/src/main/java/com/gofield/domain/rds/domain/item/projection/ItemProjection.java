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
public class ItemProjection {

    private final Long id;
    private final String name;
    private final String brandName;
    private final Long sellerId;

    private final Long bundleId;
    private final String thumbnail;
    private final String description;
    private final String itemNumber;
    private final int price;
    private final int qty;
    private final Long likeId;
    private final EItemClassificationFlag classification;
    private final EItemSpecFlag spec;
    private final EItemDeliveryFlag delivery;
    private final EItemGenderFlag gender;
    private final String tags;
    private final String option;

    @QueryProjection
    public ItemProjection(Long id, String name, String brandName, Long sellerId, Long bundleId, String thumbnail, String description, String itemNumber, int price, int qty, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.sellerId = sellerId;
        this.bundleId = bundleId;
        this.thumbnail = thumbnail;
        this.description = description;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.likeId = likeId;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.tags = tags;
        this.option = option;
    }
}
