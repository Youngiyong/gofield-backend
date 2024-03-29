package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.*;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class ItemProjection {

    private final Long id;
    private final String name;
    private final String brandName;
    private final Long sellerId;
    private final Long bundleId;
    private final Long brandId;
    private final Long categoryId;
    private final Long shippingTemplateId;
    private final String thumbnail;
    private final String description;
    private final String itemNumber;
    private final int price;
    private final int deliveryPrice;
    private final int qty;
    private final EItemStatusFlag status;
    private final Long likeId;
    private final Boolean isOption;
    private final Boolean isSoldOut;
    private final EItemClassificationFlag classification;
    private final EItemSpecFlag spec;
    private final EItemDeliveryFlag delivery;
    private final EItemGenderFlag gender;
    private final String tags;
    private final String option;
    private final LocalDateTime createDate;

    @QueryProjection
    public ItemProjection(Long id, String name, String brandName, Long sellerId, Long bundleId, Long brandId, Long categoryId, Long shippingTemplateId, String thumbnail, String description, String itemNumber, int price, int deliveryPrice, int qty, EItemStatusFlag status, Long likeId, Boolean isOption, Boolean isSoldOut, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.sellerId = sellerId;
        this.bundleId = bundleId;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.shippingTemplateId = shippingTemplateId;
        this.thumbnail = thumbnail;
        this.description = description;
        this.itemNumber = itemNumber;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.qty = qty;
        this.status = status;
        this.likeId = likeId;
        this.isOption = isOption;
        this.isSoldOut = isSoldOut;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.tags = tags;
        this.option = option;
        this.createDate = createDate;
    }
}
