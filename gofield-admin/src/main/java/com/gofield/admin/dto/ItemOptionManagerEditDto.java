package com.gofield.admin.dto;

import com.gofield.domain.rds.domain.item.EItemOptionTypeFlag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ItemOptionManagerEditDto {
    private Boolean isOption;
    private EItemOptionTypeFlag optionType;
    private List<ItemNameValueDto> optionGroupList;
    private List<ItemOptionItemDto> optionItemList;

    @Builder
    private ItemOptionManagerEditDto(Boolean isOption, EItemOptionTypeFlag optionType, List<ItemNameValueDto> optionGroupList, List<ItemOptionItemDto> optionItemList){
        this.isOption = isOption;
        this.optionType = optionType;
        this.optionGroupList = optionGroupList;
        this.optionItemList = optionItemList;
    }

    public static ItemOptionManagerEditDto of(Boolean isOption, EItemOptionTypeFlag optionType, List<ItemNameValueDto> optionGroupList, List<ItemOptionItemDto> optionItemList){
        return ItemOptionManagerEditDto.builder()
                .isOption(isOption)
                .optionType(optionType)
                .optionGroupList(optionGroupList)
                .optionItemList(optionItemList)
                .build();
    }
}
