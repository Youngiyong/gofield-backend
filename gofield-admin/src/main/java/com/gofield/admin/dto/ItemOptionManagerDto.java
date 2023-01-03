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
public class ItemOptionManagerDto {
    private Boolean isOption;
    private EItemOptionTypeFlag optionType;
    private List<ItemNameValueDto> optionGroupList;
    private List<Map<String, Object>> optionItemList;
    private List<Long> optionGroupDeleteIds;
    private List<String> optionItemListDeleteItemNumbers;

    @Builder
    private ItemOptionManagerDto(Boolean isOption, EItemOptionTypeFlag optionType, List<ItemNameValueDto> optionGroupList, List<Map<String, Object>> optionItemList, List<Long> optionGroupDeleteIds, List<String> optionItemListDeleteItemNumbers){
        this.isOption = isOption;
        this.optionType = optionType;
        this.optionGroupList = optionGroupList;
        this.optionItemList = optionItemList;
        this.optionGroupDeleteIds = optionGroupDeleteIds;
        this.optionItemListDeleteItemNumbers = optionItemListDeleteItemNumbers;
    }

    public static ItemOptionManagerDto of(Boolean isOption, EItemOptionTypeFlag optionType, List<ItemNameValueDto> optionGroupList, List<Map<String, Object>> optionItemList, List<Long> optionGroupDeleteIds, List<String> optionItemListDeleteItemNumbers){
        return ItemOptionManagerDto.builder()
                .isOption(isOption)
                .optionType(optionType)
                .optionGroupList(optionGroupList)
                .optionItemList(optionItemList)
                .optionGroupDeleteIds(optionGroupDeleteIds)
                .optionItemListDeleteItemNumbers(optionItemListDeleteItemNumbers)
                .build();
    }
}
