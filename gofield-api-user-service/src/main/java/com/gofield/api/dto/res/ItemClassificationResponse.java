package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
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
    private Long likeId;
    private EItemClassificationFlag classification;
    private EItemGenderFlag gender;
    private List<String> tags;

    @Builder
    private ItemClassificationResponse(Long id, String itemNumber, String name,  String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, List<String> tags){
        this.id = id;
        this.name = name;
        this.itemNumber = itemNumber;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.likeId = likeId;
        this.classification = classification;
        this.gender = gender;
        this.tags = tags;
    }

    public static ItemClassificationResponse of(Long id, String itemNumber, String name, String brandName, String thumbnail, int price, Long likeId, EItemClassificationFlag classification, EItemGenderFlag gender, List<String> tags){
        return ItemClassificationResponse.builder()
                .id(id)
                .itemNumber(itemNumber)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .price(price)
                .likeId(likeId)
                .classification(classification)
                .gender(gender)
                .tags(tags)
                .build();
    }

    public static  List<ItemClassificationResponse> of(List<ItemClassificationProjectionResponse> list) {
        return list
                .stream()
                .map(p -> {
                    try {
                        return ItemClassificationResponse.of(p.getId(), p.getItemNumber(), p.getName(), p.getBrandName(), p.getThumbnail(), p.getPrice(), p.getLikeId(), p.getClassification(), p.getGender(),
                               p.getTags()==null ? null : new ObjectMapper().readValue(p.getTags(), new TypeReference<List<String>>(){}
                                ));
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }
}
