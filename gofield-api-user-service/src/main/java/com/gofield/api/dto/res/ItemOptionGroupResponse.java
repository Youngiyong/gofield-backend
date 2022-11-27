package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gofield.common.exception.InternalServerException;
import com.gofield.common.model.enums.ErrorAction;
import com.gofield.common.model.enums.ErrorCode;
import com.gofield.domain.rds.domain.item.*;
import com.gofield.domain.rds.domain.item.projection.ItemProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemOptionGroupResponse {
    private Long id;
    private String groupTitle;
    private EItemOptionTypeFlag optionType;
    private List<Map<String, Object>> optionGroup;
    private List<Integer> priceGroup;
    private Boolean isEssential;
    private LocalDateTime createDate;

    @Builder
    private ItemOptionGroupResponse(Long id, String groupTitle, EItemOptionTypeFlag optionType, List<Map<String, Object>> optionGroup, List<Integer> priceGroup, Boolean isEssential, LocalDateTime createDate) {
        this.id = id;
        this.groupTitle = groupTitle;
        this.optionType = optionType;
        this.optionGroup = optionGroup;
        this.priceGroup = priceGroup;
        this.isEssential = isEssential;
        this.createDate = createDate;
    }

    public static ItemOptionGroupResponse of(Long id, String groupTitle, EItemOptionTypeFlag optionType, List<Map<String, Object>>  optionGroup, List<Integer> priceGroup, Boolean isEssential, LocalDateTime createDate){
        return ItemOptionGroupResponse.builder()
                .id(id)
                .groupTitle(groupTitle)
                .optionType(optionType)
                .optionGroup(optionGroup)
                .priceGroup(priceGroup)
                .isEssential(isEssential)
                .createDate(createDate)
                .build();
    }

    public static List<ItemOptionGroupResponse> of(List<ItemOptionGroup> list){
        return list
                .stream()
                .map(p -> {
                    try {
                        return ItemOptionGroupResponse.of(p.getId(), p.getGroupTitle(), p.getOptionType(), p.getOptionGroup()==null ? null : new ObjectMapper().readValue(p.getOptionGroup(), new TypeReference<List<Map<String, Object>>>(){}), p.getPriceGroup()==null ? null : new ObjectMapper().readValue(p.getPriceGroup(), new TypeReference<List<Integer>>(){}),  p.getIsEssential(),  p.getCreateDate());
                    } catch (JsonProcessingException e) {
                        throw new InternalServerException(ErrorCode.E500_INTERNAL_SERVER, ErrorAction.NONE, e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }
}
