package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemClassificationListResponse {
    private List<ItemClassificationResponse> list;
    private Long totalCount;

    @Builder
    private ItemClassificationListResponse(List<ItemClassificationResponse> list, Long totalCount){
        this.list = list;
        this.totalCount = totalCount;
    }

    public static ItemClassificationListResponse of(List<ItemClassificationResponse> list, Long totalCount){
        return ItemClassificationListResponse.builder()
                .list(list)
                .totalCount(totalCount)
                .build();
    }

}
