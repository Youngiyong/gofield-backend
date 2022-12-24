package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemBundleReviewListResponse {

    private List<ItemBundleReviewResponse> list;
    private PaginationResponse page;

    @Builder
    public ItemBundleReviewListResponse(List<ItemBundleReviewResponse> list, PaginationResponse page) {
        this.list = list;
        this.page = page;
    }

    public static ItemBundleReviewListResponse of(List<ItemBundleReviewResponse> list, PaginationResponse page){
        return ItemBundleReviewListResponse.builder()
                .list(list)
                .page(page)
                .build();
    }

}
