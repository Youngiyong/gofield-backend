package com.gofield.domain.rds.domain.item.projection;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ItemListProjectionResponse {
    private List<ItemClassificationProjectionResponse> list;
    private Long totalCount;

    @Builder
    public ItemListProjectionResponse(List<ItemClassificationProjectionResponse> list, Long totalCount){
        this.list = list;
        this.totalCount = totalCount;
    }

    public static ItemListProjectionResponse of(List<ItemClassificationProjectionResponse> list, Long totalCount){
        return ItemListProjectionResponse.builder()
                .list(list)
                .totalCount(totalCount)
                .build();
    }
}
