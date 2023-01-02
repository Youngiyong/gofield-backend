package com.gofield.admin.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.admin.util.AdminUtil;
import com.gofield.domain.rds.domain.item.ItemOption;
import com.gofield.domain.rds.domain.item.ItemOptionGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemOptionGroupDto {
    private String groupTitle;
    private List<ItemNamePriceDto> optionGroupList;
    private String optionGroupStr;

    @Builder
    private ItemOptionGroupDto(String groupTitle, List<ItemNamePriceDto> optionGroupList, String optionGroupStr){
        this.groupTitle = groupTitle;
        this.optionGroupList = optionGroupList;
        this.optionGroupStr = optionGroupStr;
    }

    public static ItemOptionGroupDto of(String groupTitle, String optionGroupStr, List<ItemNamePriceDto> optionGroupList){
        return ItemOptionGroupDto.builder()
                .groupTitle(groupTitle)
                .optionGroupStr(optionGroupStr)
                .optionGroupList(optionGroupList)
                .build();
    }

    public static List<ItemOptionGroupDto> of(List<ItemNameValueDto> optionGroupList){
        List<ItemOptionGroupDto> result = new ArrayList<>();
        for(ItemNameValueDto option :optionGroupList){
            String groupTitle = option.getName();
            String optionGroupStr = option.getValue();
            String[] optionStrList = optionGroupStr.split(",");
            List<ItemNamePriceDto> optionItem = new ArrayList<>();
            for(String str: optionStrList){
                optionItem.add(ItemNamePriceDto.of(str, 0));
            }
            result.add(ItemOptionGroupDto.of(groupTitle, optionGroupStr, optionItem));
        }
        return result;
    }

}
