package com.gofield.api.service.item.dto.response;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.projection.ItemBundleRecommendProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundleRecommendResponse {
    private Long id;
    private String name;
    private String brandName;
    private String thumbnail;
    private int reviewCount;
    private Double reviewScore;
    private int newLowestPrice;
    private int usedLowestPrice;
    private int lowestPrice;

    @Builder
    private ItemBundleRecommendResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, int lowestPrice){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
        this.lowestPrice = lowestPrice;
    }

    public static ItemBundleRecommendResponse of(ItemBundleRecommendProjection projection){
        return ItemBundleRecommendResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(projection.getThumbnail()))
                .reviewCount(projection.getReviewCount())
                .reviewScore(projection.getReviewScore())
                .newLowestPrice(projection.getNewLowestPrice())
                .usedLowestPrice(projection.getUsedLowestPrice())
                .lowestPrice(projection.getLowestPrice())
                .build();
    }

    public static List<ItemBundleRecommendResponse> of(List<ItemBundleRecommendProjection> list){
        return list
                .stream()
                .map(ItemBundleRecommendResponse::of)
                .collect(Collectors.toList());

    }

}
