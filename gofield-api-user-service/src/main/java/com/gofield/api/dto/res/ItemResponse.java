package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.gofield.domain.rds.domain.item.projection.ItemProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int price;
    private int deliveryPrice;
    private int qty;
    private Long likeId;
    private Boolean isOption;
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private List<String> images;
    private List<Map<String, Object>> option;
    private List<String> tags;

    private ShippingTemplateResponse shippingTemplate;

    @Builder
    private ItemResponse(Long id, String name, String brandName, String thumbnail, String itemNumber, Long bundleId,
                         int price, int deliveryPrice, int qty, Long likeId, Boolean isOption, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery,  EItemGenderFlag gender, List<String> images, List<Map<String, Object>> option, List<String> tags, ShippingTemplateResponse shippingTemplate){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.bundleId = bundleId;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.qty = qty;
        this.likeId = likeId;
        this.isOption = isOption;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.images = images;
        this.option = option;
        this.tags = tags;
        this.shippingTemplate = shippingTemplate;
    }

    public static ItemResponse of(ItemProjectionResponse projection){
        return ItemResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
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
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .images(projection.getImages().stream().map(k -> CommonUtils.makeCloudFrontUrl(k)).collect(Collectors.toList()))
                .option(projection.getOption()==null ? null : ApiUtil.strToObject(projection.getOption(), new TypeReference<List<Map<String, Object>>>(){}))
                .tags(projection.getTags()==null ? null : ApiUtil.strToObject(projection.getTags(), new TypeReference<List<String>>(){}))
                .shippingTemplate(ShippingTemplateResponse.of(projection.getShippingTemplate()))
                .build();
    }

}
