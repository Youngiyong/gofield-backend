package com.gofield.api.service.item.dto.response;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.projection.ItemBundlePopularProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundlePopularResponse {
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
    private ItemBundlePopularResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, int lowestPrice){
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

    public static ItemBundlePopularResponse of(ItemBundlePopularProjection projection){
        return ItemBundlePopularResponse.builder()
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

    public static List<ItemBundlePopularResponse> of(List<ItemBundlePopularProjection> list){
        return list
                .stream()
                .map(ItemBundlePopularResponse::of)
                .collect(Collectors.toList());

    }

}
