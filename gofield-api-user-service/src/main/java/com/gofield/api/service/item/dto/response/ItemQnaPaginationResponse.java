package com.gofield.api.service.item.dto.response;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemQnaPaginationResponse {

    private List<ItemQnaResponse> list;
    private PaginationResponse page;

    @Builder
    public ItemQnaPaginationResponse(List<ItemQnaResponse> list, PaginationResponse page) {
        this.list = list;
        this.page = page;
    }

    public static ItemQnaPaginationResponse of(List<ItemQnaResponse> list, PaginationResponse page){
        return ItemQnaPaginationResponse.builder()
                .list(list)
                .page(page)
                .build();
    }

}
