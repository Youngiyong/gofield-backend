package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.ShippingTemplate;
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
    private int deliveryPrice;
    private int qty;
    private Long likeId;
    private Boolean isOption;
    private EItemStatusFlag status;
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private List<String> images;
    private String tags;
    private String option;
    private ShippingTemplate shippingTemplate;

    @Builder
    public ItemProjectionResponse(Long id, String name, String brandName, String thumbnail, String description,  String itemNumber, Long bundleId, int price, int deliveryPrice, int qty, EItemStatusFlag status, Long likeId, Boolean isOption, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, List<String> images, ShippingTemplate shippingTemplate) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.itemNumber = itemNumber;
        this.bundleId = bundleId;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.qty = qty;
        this.status = status;
        this.likeId = likeId;
        this.isOption = isOption;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.images = images;
        this.tags = tags;
        this.option = option;
        this.shippingTemplate = shippingTemplate;
    }

    public static ItemProjectionResponse of(ItemNonMemberProjection projection, ShippingTemplate shippingTemplate, List<String> images){
        return ItemProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .description(projection.getDescription())
                .itemNumber(projection.getItemNumber())
                .bundleId(projection.getBundleId())
                .price(projection.getPrice())
                .deliveryPrice(projection.getDeliveryPrice())
                .qty(projection.getQty())
                .status(projection.getStatus())
                .isOption(projection.getIsOption())
                .classification(projection.getClassification())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .tags(projection.getTags())
                .option(projection.getOption())
                .shippingTemplate(shippingTemplate)
                .images(images)
                .build();
    }

    public static ItemProjectionResponse of(ItemProjection projection, ShippingTemplate shippingTemplate, List<String> images){
        return ItemProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .description(projection.getDescription())
                .itemNumber(projection.getItemNumber())
                .bundleId(projection.getBundleId())
                .price(projection.getPrice())
                .deliveryPrice(projection.getDeliveryPrice())
                .qty(projection.getQty())
                .status(projection.getStatus())
                .isOption(projection.getIsOption())
                .classification(projection.getClassification())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .likeId(projection.getLikeId())
                .tags(projection.getTags())
                .option(projection.getOption())
                .shippingTemplate(shippingTemplate)
                .images(images)
                .build();
    }


    public static ItemProjectionResponse of(Long id, String name, String brandName, String thumbnail, String description, String itemNumber, Long bundleId, int price, int deliveryPrice,  int qty, EItemStatusFlag status, Long likeId, Boolean isOption, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, List<String> images, ShippingTemplate shippingTemplate){
        return ItemProjectionResponse.builder()
                .id(id)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .description(description)
                .itemNumber(itemNumber)
                .bundleId(bundleId)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .status(status)
                .qty(qty)
                .likeId(likeId)
                .isOption(isOption)
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
