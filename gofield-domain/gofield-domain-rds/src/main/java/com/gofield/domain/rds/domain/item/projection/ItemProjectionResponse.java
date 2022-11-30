package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.gofield.domain.rds.domain.seller.ShippingTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ItemProjectionResponse {

    private Long id;
    private String name;
    private String brandName;
    private String thumbnail;
    private String description;
    private String itemNumber;

    private Long bundleId;
    private int price;
    private int qty;
    private Long likeId;
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private List<String> images;
    private String tags;
    private String option;

    private ShippingTemplate shippingTemplate;

    @Builder
    public ItemProjectionResponse(Long id, String name, String brandName, String thumbnail, String description,  String itemNumber, Long bundleId, int price, int qty, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, List<String> images, ShippingTemplate shippingTemplate) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.itemNumber = itemNumber;
        this.bundleId = bundleId;
        this.price = price;
        this.qty = qty;
        this.likeId = likeId;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.images = images;
        this.tags = tags;
        this.option = option;
        this.shippingTemplate = shippingTemplate;
    }

    public static ItemProjectionResponse of(Long id, String name, String brandName, String thumbnail, String description, String itemNumber, Long bundleId, int price, int qty, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, List<String> images, ShippingTemplate shippingTemplate){
        return ItemProjectionResponse.builder()
                .id(id)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .description(description)
                .itemNumber(itemNumber)
                .bundleId(bundleId)
                .price(price)
                .qty(qty)
                .likeId(likeId)
                .classification(classification)
                .spec(spec)
                .delivery(delivery)
                .gender(gender)
                .images(images)
                .tags(tags)
                .option(option)
                .shippingTemplate(shippingTemplate)
                .build();
    }

}
