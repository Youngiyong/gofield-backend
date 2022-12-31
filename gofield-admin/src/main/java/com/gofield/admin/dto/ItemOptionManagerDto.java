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
    private List<ItemOptionGroupDto> optionGroupList;
    private List<Map<String, Object>> optionItemList;

    @Builder
    private ItemOptionManagerDto(Boolean isOption, EItemOptionTypeFlag optionType, List<ItemOptionGroupDto> optionGroupList, List<Map<String, Object>> optionItemList){
        this.isOption = isOption;
        this.optionType = optionType;
        this.optionGroupList = optionGroupList;
        this.optionItemList = optionItemList;
    }

//    public static ItemOptionManagerDto of(Boolean isOption, EItemOptionTypeFlag optionType, List<ItemOptionGroupDto> optionGroupList, List<ItemOptionItemDto> optionItemList){
//        return ItemOptionManagerDto.builder()
//                .isOption(isOption)
//                .optionType(optionType)
//                .optionGroupList(optionGroupList)
//                .optionItemList(optionItemList)
//                .build();
//    }
}
