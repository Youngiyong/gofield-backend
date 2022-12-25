package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemClassificationPaginationResponse {
    private List<ItemClassificationResponse> list;
    private PaginationResponse page;

    @Builder
    private ItemClassificationPaginationResponse(List<ItemClassificationResponse> list, PaginationResponse page){
        this.list = list;
        this.page = page;
    }

    public static ItemClassificationPaginationResponse of(List<ItemClassificationResponse> list, PaginationResponse page){
        return ItemClassificationPaginationResponse.builder()
                .list(list)
                .page(page)
                .build();
    }

}
