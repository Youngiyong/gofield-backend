package com.gofield.domain.rds.domain.item.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemBundleImageProjection {

    private final Long id;
    private final String name;
    private final String brandName;
    private final String thumbnail;
    private final String description;
    private final int reviewCount;
    private final Double reviewScore;
    private final int newLowestPrice;
    private final int usedLowestPrice;

    @QueryProjection
    public ItemBundleImageProjection(Long id, String name, String brandName, String thumbnail, String description, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
    }
}
