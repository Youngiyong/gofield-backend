package com.gofield.api.service.item.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.gofield.domain.rds.domain.item.projection.ItemProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String brandName;
    private String thumbnail;
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
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private List<String> images;
    private List<Map<String, Object>> option;
    private List<String> tags;
    private ShippingTemplateResponse shippingTemplate;
    private String description;

    @Builder
    private ItemResponse(Long id, String name, String brandName, String thumbnail, String itemNumber, Long bundleId, Long categoryId, Long brandId,
                         int price, int deliveryPrice, int qty, Long likeId, Boolean isOption, Boolean isSoldOut, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery,  EItemGenderFlag gender, List<String> images, List<Map<String, Object>> option, List<String> tags, ShippingTemplateResponse shippingTemplate, String description){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.bundleId = bundleId;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.qty = qty;
        this.likeId = likeId;
        this.isOption = isOption;
        this.isSoldOut = isSoldOut;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.images = images;
        this.option = option;
        this.tags = tags;
        this.shippingTemplate = shippingTemplate;
        this.description = description;
    }

    public static ItemResponse of(ItemProjectionResponse projection){
        List<String> tags = projection.getTags()==null || projection.getTags().equals("") ? new ArrayList<>() : ObjectMapperUtils.strToObject(projection.getTags(), new TypeReference<List<String>>(){});

        if(tags!=null){
            tags.add(projection.getGender().getDescription());
        }

        tags.add(0, projection.getClassification().getDescription());
        tags.add(1, projection.getSpec().getDescription());
        tags.add(2, projection.getDelivery().getDescription());

        List<Map<String, Object>> option = projection.getOption()==null || projection.getOption().equals("") ? new ArrayList<>() :  ObjectMapperUtils.strToObject(projection.getOption(), new TypeReference<List<Map<String, Object>>>(){});
        return ItemResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandId(projection.getBrandId())
                .categoryId(projection.getCategoryId())
                .brandName(projection.getBrandName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(projection.getThumbnail()))
                .itemNumber(projection.getItemNumber())
                .bundleId(projection.getBundleId())
                .price(projection.getPrice())
                .deliveryPrice(projection.getDeliveryPrice())
                .qty(projection.getQty())
                .likeId(projection.getLikeId())
                .isOption(projection.getIsOption())
                .classification(projection.getClassification())
                .isSoldOut(projection.getIsSoldOut())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .images(projection.getImages().stream().map(k -> CommonUtils.makeCloudFrontUrl(k)).collect(Collectors.toList()))
                .option(option)
                .tags(tags)
                .shippingTemplate(ShippingTemplateResponse.of(projection.getShippingTemplate()))
                .description(projection.getDescription())
                .build();
    }

}
