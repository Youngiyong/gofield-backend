package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemOptionDetailResponse {
    private List<ItemOptionGroupResponse> optionGroupList;
    private List<ItemOptionResponse> optionList;

    @Builder
    private ItemOptionDetailResponse(List<ItemOptionGroupResponse> optionGroupList, List<ItemOptionResponse> optionList){
        this.optionGroupList = optionGroupList;
        this.optionList = optionList;
    }

    public static ItemOptionDetailResponse of(List<ItemOptionGroupResponse> optionGroupList, List<ItemOptionResponse> optionList){
        return ItemOptionDetailResponse.builder()
                .optionGroupList(optionGroupList)
                .optionList(optionList)
                .build();
    }
}
