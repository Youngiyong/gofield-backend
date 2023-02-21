package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainResponse {

    private List<ItemBundlePopularResponse> popularBundleList;
    private List<ItemBundleRecommendResponse> recommendBundleList;
    private List<ItemBundleRecommendResponse> categoryBundleList;

    @Builder
    private MainResponse(List<ItemBundlePopularResponse> popularBundleList, List<ItemBundleRecommendResponse> recommendBundleList, List<ItemBundleRecommendResponse> categoryBundleList){
        this.popularBundleList = popularBundleList;
        this.recommendBundleList = recommendBundleList;
        this.categoryBundleList = categoryBundleList;
    }

    public static MainResponse of(List<ItemBundlePopularResponse> popularBundleList, List<ItemBundleRecommendResponse> recommendBundleList, List<ItemBundleRecommendResponse> categoryBundleList){
        return MainResponse.builder()
                .popularBundleList(popularBundleList)
                .recommendBundleList(recommendBundleList)
                .categoryBundleList(categoryBundleList)
                .build();
    }
}
