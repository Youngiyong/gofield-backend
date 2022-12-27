package com.gofield.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemDetailOptionDto {

    private List<ItemKeyValueDto> optionList;

    @Builder
    public ItemDetailOptionDto(List<ItemKeyValueDto> optionList){
        this.optionList = optionList;
    }

    public static ItemDetailOptionDto of(List<ItemKeyValueDto> optionList){
        return ItemDetailOptionDto.builder()
                .optionList(optionList)
                .build();
    }
}
