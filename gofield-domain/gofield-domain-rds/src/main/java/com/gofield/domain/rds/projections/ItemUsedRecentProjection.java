package com.gofield.domain.rds.projections;

import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemUsedRecentProjection {

    private final Long id;
    private final String itemNumber;
    private final String brandName;
    private final String thumbnail;
    private final int price;
    private final Long likeId;
    private final EItemClassificationFlag classification;

    @QueryProjection
    public ItemUsedRecentProjection(Long id, String itemNumber, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.likeId = likeId;
        this.classification = classification;
    }
}
