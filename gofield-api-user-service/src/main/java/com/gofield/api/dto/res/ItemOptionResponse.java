package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
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
    private EItemOptionTypeFlag optionType;
    private EItemStatusFlag status;
    private int price;
    private int qty;
    private Boolean isUse;
    private LocalDateTime createDate;

    @Builder
    private ItemOptionResponse(Long id, Long itemId, String itemNumber, List<String> name, EItemOptionTypeFlag optionType, EItemStatusFlag status, int price, int qty, Boolean isUse, LocalDateTime createDate) {
        this.id = id;
        this.itemId = itemId;
        this.itemNumber = itemNumber;
        this.name = name;
        this.optionType = optionType;
        this.status = status;
        this.price = price;
        this.qty = qty;
        this.isUse = isUse;
        this.createDate = createDate;
    }

    public static ItemOptionResponse of(Long id, Long itemId, String itemNumber, List<String> name, EItemOptionTypeFlag optionType, EItemStatusFlag status,  int price, int qty, Boolean isUse, LocalDateTime createDate){
        return ItemOptionResponse
                .builder()
                .id(id)
                .itemId(itemId)
                .itemNumber(itemNumber)
                .name(name)
                .optionType(optionType)
                .status(status)
                .price(price)
                .qty(qty)
                .isUse(isUse)
                .createDate(createDate)
                .build();
    }

    public static List<ItemOptionResponse> of(List<ItemOptionProjection> list){
        return list
                .stream()
                .map(p -> {
                    try {
                        return ItemOptionResponse.of(p.getId(), p.getItemId(), p.getItemNumber(), p.getName()==null ? null : new ObjectMapper().readValue(p.getName(), new TypeReference<List<String>>(){}), p.getOptionType(), p.getStatus(),  p.getPrice(), p.getQty(), p.getIsUse(), p.getCreateDate());
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

}
