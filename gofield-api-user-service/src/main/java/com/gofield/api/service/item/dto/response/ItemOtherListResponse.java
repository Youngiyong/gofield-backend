package com.gofield.api.service.item.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemOtherListResponse {
    private List<ItemClassificationResponse> recommendItemList;
    private List<ItemClassificationResponse> otherItemList;

    @Builder
    private ItemOtherListResponse(List<ItemClassificationResponse> recommendItemList, List<ItemClassificationResponse> otherItemList){
        this.recommendItemList = recommendItemList;
        this.otherItemList = otherItemList;
    }

    public static ItemOtherListResponse of(List<ItemClassificationResponse> recommendItemList, List<ItemClassificationResponse> otherItemList){
        return ItemOtherListResponse.builder()
                .recommendItemList(recommendItemList)
                .otherItemList(otherItemList)
                .build();
    }

}
