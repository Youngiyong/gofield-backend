package com.gofield.api.dto.res;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.projections.ItemBundlePopularProjection;
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

    @Builder
    private ItemBundlePopularResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
    }

    public static ItemBundlePopularResponse of(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice){
        return ItemBundlePopularResponse.builder()
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

    public static List<ItemBundlePopularResponse> of(List<ItemBundlePopularProjection> list){
        return list
                .stream()
                .map(p -> ItemBundlePopularResponse.of(p.getId(), p.getName(), p.getBrandName(), p.getThumbnail(), p.getReviewCount(), p.getReviewScore(), p.getNewLowestPrice(), p.getUsedLowestPrice()))
                .collect(Collectors.toList());

    }

}
