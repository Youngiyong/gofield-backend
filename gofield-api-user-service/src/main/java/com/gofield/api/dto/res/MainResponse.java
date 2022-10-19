package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainResponse {

    private List<CategoryMainResponse> categoryList;
    private List<BannerResponse> bannerList;

    @Builder
    private MainResponse(List<CategoryMainResponse> categoryList, List<BannerResponse> bannerList){
        this.categoryList = categoryList;
        this.bannerList = bannerList;
    }

    public static MainResponse of(List<CategoryMainResponse> categoryList, List<BannerResponse> bannerList){
        return MainResponse.builder()
                .categoryList(categoryList)
                .bannerList(bannerList)
                .build();
    }
}
