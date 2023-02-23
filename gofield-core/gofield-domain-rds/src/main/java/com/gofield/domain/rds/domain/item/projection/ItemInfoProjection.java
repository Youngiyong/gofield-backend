package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class ItemInfoProjection {

    private final Long id;
    private final String name;
    private final EItemClassificationFlag classification;
    private final int price;
    private final String categoryName;
    private final EItemStatusFlag status;
    private final String thumbnail;

    private final Boolean isOption;
    private final LocalDateTime createDate;

    @QueryProjection
    public ItemInfoProjection(Long id, String name, EItemClassificationFlag classification, int price, String categoryName, EItemStatusFlag status, String thumbnail, Boolean isOption, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.price = price;
        this.categoryName = categoryName;
        this.status = status;
        this.thumbnail = thumbnail;
        this.isOption = isOption;
        this.createDate = createDate;
    }
}
