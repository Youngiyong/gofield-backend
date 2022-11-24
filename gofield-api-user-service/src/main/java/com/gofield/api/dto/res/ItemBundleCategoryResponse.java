package com.gofield.api.dto.res;

import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
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

    @Builder
    private ItemBundleCategoryResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
    }

    public static ItemBundleCategoryResponse of(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice){
        return ItemBundleCategoryResponse.builder()
                .id(id)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail)
                .reviewCount(reviewCount)
                .reviewScore(reviewScore)
                .newLowestPrice(newLowestPrice)
                .usedLowestPrice(usedLowestPrice)
                .build();
    }

    public static List<ItemBundleCategoryResponse> of(List<ItemBundlePopularProjection> list){
        return list
                .stream()
                .map(p -> ItemBundleCategoryResponse.of(p.getId(), p.getName(), p.getBrandName(), p.getThumbnail(), p.getReviewCount(), p.getReviewScore(), p.getNewLowestPrice(), p.getUsedLowestPrice()))
                .collect(Collectors.toList());

    }

}
