package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemClassificationProjection {

    private final Long id;
    private final String itemNumber;
    private final String name;
    private final String brandName;
    private final String thumbnail;
    private final int price;
    private final Long likeId;
    private final EItemClassificationFlag classification;
    private final EItemGenderFlag gender;
    private final String tags;

    @QueryProjection
    public ItemClassificationProjection(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, String tags) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.likeId = likeId;
        this.classification = classification;
        this.gender = gender;
        this.tags = tags;
    }
}
