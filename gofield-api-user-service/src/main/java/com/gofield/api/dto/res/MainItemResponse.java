package com.gofield.api.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MainItemResponse {

    private List<ItemBundlePopularResponse> popularBundleList;
    private List<ItemBundleRecommendResponse> recommendBundleList;
    private List<ItemBundleRecommendResponse> categoryBundleList;

    @Builder
    private MainItemResponse(List<ItemBundlePopularResponse> popularBundleList, List<ItemBundleRecommendResponse> recommendBundleList, List<ItemBundleRecommendResponse> categoryBundleList){
        this.popularBundleList = popularBundleList;
        this.recommendBundleList = recommendBundleList;
        this.categoryBundleList = categoryBundleList;
    }

    public static MainItemResponse of(List<ItemBundlePopularResponse> popularBundleList, List<ItemBundleRecommendResponse> recommendBundleList, List<ItemBundleRecommendResponse> categoryBundleList){
        return MainItemResponse.builder()
                .popularBundleList(popularBundleList)
                .recommendBundleList(recommendBundleList)
                .categoryBundleList(categoryBundleList)
                .build();
    }
}
