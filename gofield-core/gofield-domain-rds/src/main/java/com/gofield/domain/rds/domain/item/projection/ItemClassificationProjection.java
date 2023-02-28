package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class ItemClassificationProjection {

    private final Long id;
    private final String itemNumber;
    private final String name;
    private final String brandName;
    private final String thumbnail;
    private final int price;
    private final int deliveryPrice;
    private final Long likeId;
    private final EItemClassificationFlag classification;
    private final EItemSpecFlag spec;
    private final EItemDeliveryFlag delivery;
    private final EItemGenderFlag gender;
    private final String tags;

    private final Boolean isSoldOut;
    private final LocalDateTime createDate;

    @QueryProjection
    public ItemClassificationProjection(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, int deliveryPrice, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, Boolean isSoldOut, LocalDateTime createDate) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.likeId = likeId;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.tags = tags;
        this.isSoldOut = isSoldOut;
        this.createDate = createDate;
    }
}
