package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import com.gofield.domain.rds.projections.ItemClassificationProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
                .thumbnail(thumbnail)
                .price(price)
                .likeId(likeId)
                .classification(classification)
                .gender(gender)
                .option(option)
                .build();
    }

    public static  List<ItemClassificationResponse> of(List<ItemClassificationProjection> result) {
        return result
                .stream()
                .map(p -> {
                    try {
                        return ItemClassificationResponse.of(p.getId(), p.getItemNumber(), p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getLikeId(), p.getClassification(), p.getGender(),
                                new ObjectMapper().readValue(p.getOption(), new TypeReference<List<Map<String, Object>>>(){}
                                ));
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }
}
