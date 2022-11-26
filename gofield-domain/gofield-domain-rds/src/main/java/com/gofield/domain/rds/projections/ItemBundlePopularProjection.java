package com.gofield.domain.rds.projections;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemBundlePopularProjection {

    private final Long id;
    private final String name;
    private final String brandName;
    private final String thumbnail;
    private final int reviewCount;
    private final Double reviewScore;
    private final int newLowestPrice;
    private final int usedLowestPrice;

    @QueryProjection
    public ItemBundlePopularProjection(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
    }
}