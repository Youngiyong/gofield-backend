package com.gofield.api.service.item.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.EItemDeliveryFlag;
import com.gofield.domain.rds.domain.item.EItemGenderFlag;
import com.gofield.domain.rds.domain.item.EItemSpecFlag;
import com.gofield.domain.rds.domain.item.projection.ItemClassificationProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private EItemSpecFlag spec;
    private EItemGenderFlag gender;
    private List<String> tags;
    private LocalDateTime createDate;

    @Builder
    private ItemClassificationResponse(Long id, String itemNumber, String name,  String brandName, String thumbnail, int price, int deliveryPrice, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery,  EItemGenderFlag gender, List<String> tags, LocalDateTime createDate){
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
        this.spec = spec;
        this.gender = gender;
        this.tags = tags;
        this.createDate = createDate;
    }

    public static ItemClassificationResponse of(ItemClassificationProjectionResponse projection){
        List<String> tags = projection.getTags()==null || projection.getTags().equals("") ? new ArrayList<>() : ObjectMapperUtils.strToObject(projection.getTags(), new TypeReference<List<String>>(){});

        if(tags!=null){
            while (tags.size()>2){
                tags.remove(2);
            }
        }
        tags.add(0, projection.getClassification().getDescription());
        tags.add(1, projection.getSpec().getDescription());

        return ItemClassificationResponse.builder()
                .id(projection.getId())
                .itemNumber(projection.getItemNumber())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(projection.getThumbnail()))
                .price(projection.getPrice())
                .deliveryPrice(projection.getDeliveryPrice())
                .likeId(projection.getLikeId())
                .classification(projection.getClassification())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .tags(tags)
                .createDate(projection.getCreateDate())
                .build();
    }

    public static  List<ItemClassificationResponse> of(List<ItemClassificationProjectionResponse> list) {
        return list
                .stream()
                .map(ItemClassificationResponse::of)
                .collect(Collectors.toList());
    }
}
