package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FaqListResponse {

    private List<FaqResponse> list;
    private PaginationResponse page;

    @Builder
    public FaqListResponse(List<FaqResponse> list, PaginationResponse page) {
        this.list = list;
        this.page = page;
    }

    public static FaqListResponse of(List<FaqResponse> list, PaginationResponse page){
        return FaqListResponse.builder()
                .list(list)
                .page(page)
                .build();
    }

}
