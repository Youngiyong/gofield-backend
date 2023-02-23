package com.gofield.api.service.item.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.common.utils.ObjectMapperUtils;
import com.gofield.domain.rds.domain.item.*;
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

    public static ItemOptionGroupResponse of(ItemOptionGroup itemOptionGroup){
        return ItemOptionGroupResponse.builder()
                .id(itemOptionGroup.getId())
                .groupTitle(itemOptionGroup.getGroupTitle())
                .optionType(itemOptionGroup.getOptionType())
                .optionGroup(itemOptionGroup.getOptionGroup()==null ? null : ObjectMapperUtils.strToObject(itemOptionGroup.getOptionGroup(), new TypeReference<List<Map<String, Object>>>(){}))
                .priceGroup(itemOptionGroup.getPriceGroup()==null ? null :  ObjectMapperUtils.strToObject(itemOptionGroup.getPriceGroup(), new TypeReference<List<Integer>>(){}))
                .isEssential(itemOptionGroup.getIsEssential())
                .createDate(itemOptionGroup.getCreateDate())
                .build();
    }

    public static List<ItemOptionGroupResponse> of(List<ItemOptionGroup> list){
        return list
                .stream()
                .map(ItemOptionGroupResponse::of)
                .collect(Collectors.toList());
    }
}
