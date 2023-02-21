package com.gofield.api.dto.res;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.projection.ItemBundlePopularProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundleCategoryResponse {
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
    private ItemBundleCategoryResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, int lowestPrice){
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

    public static ItemBundleCategoryResponse of(ItemBundlePopularProjection projection){
        return ItemBundleCategoryResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(projection.getThumbnail()))
                .reviewCount(projection.getReviewCount())
                .reviewScore(projection.getReviewScore())
                .newLowestPrice(projection.getNewLowestPrice())
                .usedLowestPrice(projection.getUsedLowestPrice())
                .lowestPrice(projection.getNewLowestPrice()>projection.getUsedLowestPrice() ? projection.getUsedLowestPrice() : projection.getNewLowestPrice())
                .build();
    }

    public static List<ItemBundleCategoryResponse> of(List<ItemBundlePopularProjection> list){
        return list
                .stream()
                .map(ItemBundleCategoryResponse::of)
                .collect(Collectors.toList());

    }

}
