package com.gofield.api.service.item.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import com.gofield.domain.rds.domain.item.EItemStatusFlag;
import com.gofield.domain.rds.domain.item.projection.ItemOptionProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemOptionResponse {
    private Long id;
    private Long itemId;
    private String itemNumber;
    private List<String> name;
    private String optionName;
    private EItemOptionTypeFlag optionType;
    private EItemStatusFlag status;
    private int price;
    private int optionPrice;
    private int qty;
    private Boolean isUse;
    private LocalDateTime createDate;

    @Builder
    private ItemOptionResponse(Long id, Long itemId, String itemNumber, List<String> name, String optionName, EItemOptionTypeFlag optionType, EItemStatusFlag status, int price, int optionPrice, int qty, Boolean isUse, LocalDateTime createDate) {
        this.id = id;
        this.itemId = itemId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.optionName = optionName;
        this.optionType = optionType;
        this.status = status;
        this.price = price;
        this.optionPrice = optionPrice;
        this.qty = qty;
        this.isUse = isUse;
        this.createDate = createDate;
    }

    public static ItemOptionResponse of(ItemOptionProjection projection){
        return ItemOptionResponse
                .builder()
                .id(projection.getId())
                .itemId(projection.getItemId())
                .itemNumber(projection.getItemNumber())
                .name(projection.getName()==null ? null : ObjectMapperUtils.strToObject(projection.getName(), new TypeReference<List<String>>(){}))
                .optionName(projection.getOptionName())
                .optionType(projection.getOptionType())
                .status(projection.getStatus())
                .price(projection.getPrice())
                .optionPrice(projection.getOptionPrice())
                .qty(projection.getQty())
                .isUse(projection.getIsUse())
                .createDate(projection.getCreateDate())
                .build();
    }

    public static List<ItemOptionResponse> of(List<ItemOptionProjection> list){
        return list
                .stream()
                .map(ItemOptionResponse::of)
                .collect(Collectors.toList());
    }

}
