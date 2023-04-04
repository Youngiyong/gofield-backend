package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.ShippingTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
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
    private Long categoryId;
    private Long brandId;
    private int price;
    private int deliveryPrice;
    private int qty;
    private Long likeId;
    private Boolean isOption;
    private Boolean isSoldOut;
    private EItemStatusFlag status;
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private List<String> images;
    private String tags;
    private String option;
    private ShippingTemplate shippingTemplate;

    private LocalDateTime createDate;

    @Builder
    public ItemProjectionResponse(Long id, String name, String brandName, String thumbnail, String description,  String itemNumber, Long bundleId, Long categoryId, Long brandId, int price, int deliveryPrice, int qty, EItemStatusFlag status, Long likeId, Boolean isOption, Boolean isSoldOut, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, List<String> images, ShippingTemplate shippingTemplate, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.itemNumber = itemNumber;
        this.bundleId = bundleId;
        this.categoryId = categoryId;
        this.brandId = brandId;
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
        this.images = images;
        this.tags = tags;
        this.option = option;
        this.shippingTemplate = shippingTemplate;
        this.createDate = createDate;
    }

    public static ItemProjectionResponse of(ItemNonMemberProjection projection, ShippingTemplate shippingTemplate, List<String> images){
        return ItemProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .brandId(projection.getBrandId())
                .categoryId(projection.getCategoryId())
                .thumbnail(projection.getThumbnail())
                .description(projection.getDescription())
                .itemNumber(projection.getItemNumber())
                .bundleId(projection.getBundleId())
                .price(projection.getPrice())
                .deliveryPrice(projection.getDeliveryPrice())
                .qty(projection.getQty())
                .status(projection.getStatus())
                .isOption(projection.getIsOption())
                .isSoldOut(projection.getIsSoldOut())
                .classification(projection.getClassification())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .tags(projection.getTags())
                .option(projection.getOption())
                .shippingTemplate(shippingTemplate)
                .images(images)
                .createDate(projection.getCreateDate())
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
                .brandId(projection.getBrandId())
                .categoryId(projection.getCategoryId())
                .price(projection.getPrice())
                .deliveryPrice(projection.getDeliveryPrice())
                .qty(projection.getQty())
                .isSoldOut(projection.getIsSoldOut())
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
                .createDate(projection.getCreateDate())
                .build();
    }


    public static ItemProjectionResponse of(Long id, String name, String brandName, String thumbnail, String description, String itemNumber, Long bundleId, Long brandId, Long categoryId, int price, int deliveryPrice,  int qty, EItemStatusFlag status, Long likeId, Boolean isOption, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String tags, String option, List<String> images, ShippingTemplate shippingTemplate, LocalDateTime createDate){
        return ItemProjectionResponse.builder()
                .id(id)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .description(description)
                .itemNumber(itemNumber)
                .bundleId(bundleId)
                .brandId(brandId)
                .categoryId(categoryId)
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
                .createDate(createDate)
                .build();
    }

}
