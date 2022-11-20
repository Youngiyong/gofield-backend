package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainItemResponse {

    private List<MainItemResponse> bannerList;

    @Builder
    private MainItemResponse(List<CategoryMainResponse> categoryList, List<MainItemResponse> bannerList){
        this.bannerList = bannerList;
    }

    public static MainItemResponse of(List<CategoryMainResponse> categoryList, List<MainItemResponse> bannerList){
        return MainItemResponse.builder()
                .categoryList(categoryList)
                .bannerList(bannerList)
                .build();
    }
}
