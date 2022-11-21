package com.gofield.api.dto.res;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.entity.category.Category;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import com.gofield.domain.rds.projections.ItemRecentProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemRecentResponse {
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
    private ItemRecentResponse(Long id, String itemNumber, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender,  List<Map<String, Object>> option){
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

    public static ItemRecentResponse of(Long id, String itemNumber, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender,  List<Map<String, Object>> option){
        return ItemRecentResponse.builder()
                .id(id)
                .itemNumber(itemNumber)
                .brandName(brandName)
                .thumbnail(thumbnail==null ? thumbnail : Constants.CDN_URL.concat(thumbnail))
                .price(price)
                .likeId(likeId)
                .classification(classification)
                .gender(gender)
                .option(option)
                .build();
    }

}
