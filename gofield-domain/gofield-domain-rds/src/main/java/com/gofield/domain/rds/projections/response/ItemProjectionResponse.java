package com.gofield.domain.rds.projections.response;

import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemDeliveryFlag;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import com.gofield.domain.rds.enums.item.EItemSpecFlag;
import com.gofield.domain.rds.projections.ItemProjection;
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
    private String itemNumber;
    private int price;
    private int qty;
    private Long likeId;
    private EItemClassificationFlag classification;
    private EItemSpecFlag spec;
    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;
    private String option;
    private List<String> images;

    @Builder
    public ItemProjectionResponse(Long id, String name, String brandName, String thumbnail, String itemNumber, int price, int qty, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery, EItemGenderFlag gender, String option, List<String> images) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.itemNumber = itemNumber;
        this.price = price;
        this.qty = qty;
        this.likeId = likeId;
        this.classification = classification;
        this.spec = spec;
        this.delivery = delivery;
        this.gender = gender;
        this.option = option;
        this.images = images;
    }

    public static ItemProjectionResponse of(ItemProjection projection, List<String> images){
        return ItemProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .itemNumber(projection.getItemNumber())
                .price(projection.getPrice())
                .qty(projection.getQty())
                .likeId(projection.getLikeId())
                .classification(projection.getClassification())
                .spec(projection.getSpec())
                .delivery(projection.getDelivery())
                .gender(projection.getGender())
                .option(projection.getOption())
                .images(images)
                .build();
    }
}
