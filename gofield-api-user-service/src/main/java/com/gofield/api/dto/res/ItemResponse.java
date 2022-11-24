package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.Constants;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.entity.item.Item;
import com.gofield.domain.rds.enums.item.EItemClassificationFlag;
import com.gofield.domain.rds.enums.item.EItemDeliveryFlag;
import com.gofield.domain.rds.enums.item.EItemGenderFlag;
import com.gofield.domain.rds.enums.item.EItemSpecFlag;
import com.gofield.domain.rds.projections.response.ItemBundleImageProjectionResponse;
import com.gofield.domain.rds.projections.response.ItemProjectionResponse;
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

    private int price;

    private int qty;
    private Long likeId;

    private EItemClassificationFlag classification;

    private EItemSpecFlag spec;

    private EItemDeliveryFlag delivery;
    private EItemGenderFlag gender;

    private List<String> images;

    private List<Map<String, Object>> option;

    @Builder
    private ItemResponse(Long id, String name, String brandName, String thumbnail, String itemNumber,
                         int price, int qty, Long likeId, EItemClassificationFlag classification, EItemSpecFlag spec, EItemDeliveryFlag delivery,  EItemGenderFlag gender, List<String> images, List<Map<String, Object>> option){
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
        this.images = images;
        this.option = option;
    }

    public static ItemResponse of(ItemProjectionResponse projection){
        try {
            return ItemResponse.builder()
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
                    .images(projection.getImages())
                    .option(new ObjectMapper().readValue(projection.getOption(), new TypeReference<List<Map<String, Object>>>(){}))
                    .build();
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
        }
    }

}
