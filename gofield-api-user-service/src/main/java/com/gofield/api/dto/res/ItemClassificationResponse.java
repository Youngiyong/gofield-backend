package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.projection.ItemClassificationProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemClassificationResponse {
    private Long id;
    private String itemNumber;
    private String name;
    private String brandName;
    private String thumbnail;
    private int price;
    private int deliveryPrice;
    private Long likeId;
    private EItemDeliveryFlag delivery;
    private EItemClassificationFlag classification;
    private EItemGenderFlag gender;
    private List<String> tags;

    @Builder
    private ItemClassificationResponse(Long id, String itemNumber, String name,  String brandName, String thumbnail, int price, int deliveryPrice, Long likeId, EItemClassificationFlag classification, EItemDeliveryFlag delivery,  EItemGenderFlag gender, List<String> tags){
        this.id = id;
        this.name = name;
        this.itemNumber = itemNumber;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.likeId = likeId;
        this.delivery = delivery;
        this.classification = classification;
        this.gender = gender;
        this.tags = tags;
    }

    public static ItemClassificationResponse of(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, int deliveryPrice,  Long likeId, EItemClassificationFlag classification, EItemDeliveryFlag delivery, EItemGenderFlag gender, List<String> tags){
        return ItemClassificationResponse.builder()
                .id(id)
                .itemNumber(itemNumber)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .price(price)
                .deliveryPrice(deliveryPrice)
                .likeId(likeId)
                .classification(classification)
                .delivery(delivery)
                .gender(gender)
                .tags(tags)
                .build();
    }

    public static  List<ItemClassificationResponse> of(List<ItemClassificationProjectionResponse> list) {
        return list
                .stream()
                .map(p -> ItemClassificationResponse.of(p.getId(), p.getItemNumber(), p.getName(), p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getDeliveryPrice(), p.getLikeId(), p.getClassification(), p.getDelivery(),  p.getGender(),
                               p.getTags()==null ? null : ApiUtil.strToObject(p.getTags(), new TypeReference<List<String>>(){})))
                .collect(Collectors.toList());
    }
}
