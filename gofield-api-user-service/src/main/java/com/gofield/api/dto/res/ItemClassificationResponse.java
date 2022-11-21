package com.gofield.api.dto.res;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Getter
@NoArgsConstructor
public class ItemClassificationResponse {
    private Long id;
    private String itemNumber;
    private String brandName;
    private String thumbnail;
    private int price;
    private Long likeId;
    private EItemClassificationFlag classification;
    private EItemGenderFlag gender;
    private List<Map<String, Object>> option;

    @Builder
    private ItemClassificationResponse(Long id, String itemNumber, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, List<Map<String, Object>> option){
        this.id = id;
        this.itemNumber = itemNumber;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.likeId = likeId;
        this.classification = classification;
        this.gender = gender;
        this.option = option;
    }

    public static ItemClassificationResponse of(Long id, String itemNumber, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, List<Map<String, Object>> option){
        return ItemClassificationResponse.builder()
                .id(id)
                .itemNumber(itemNumber)
                .brandName(brandName)
                .thumbnail(thumbnail==null ? null : Constants.CDN_URL.concat(thumbnail))
                .price(price)
                .likeId(likeId)
                .classification(classification)
                .gender(gender)
                .option(option)
                .build();
    }

}
