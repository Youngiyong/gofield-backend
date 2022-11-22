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

    private List<ItemClassificationResponse> classificationItemList;

    @Builder
    private MainItemResponse(List<ItemBundlePopularResponse> popularBundleList, List<ItemBundleRecommendResponse> recommendBundleList, List<ItemBundleRecommendResponse> categoryBundleList,  List<ItemClassificationResponse> classificationItemList){
        this.popularBundleList = popularBundleList;
        this.recommendBundleList = recommendBundleList;
        this.categoryBundleList = categoryBundleList;
        this.classificationItemList = classificationItemList;
    }

    public static MainItemResponse of(List<ItemBundlePopularResponse> popularBundleList, List<ItemBundleRecommendResponse> recommendBundleList, List<ItemBundleRecommendResponse> categoryBundleList, List<ItemClassificationResponse> classificationItemList){
        return MainItemResponse.builder()
                .popularBundleList(popularBundleList)
                .recommendBundleList(recommendBundleList)
                .categoryBundleList(categoryBundleList)
                .classificationItemList(classificationItemList)
                .build();
    }
}
