package com.gofield.api.dto.res;

import com.gofield.common.model.Constants;
import com.gofield.domain.rds.projections.ItemBundleRecommendProjection;
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

    @Builder
    private ItemBundleRecommendResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
    }

    public static ItemBundleRecommendResponse of(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice){
        return ItemBundleRecommendResponse.builder()
                .id(id)
                .name(name)
                .brandName(brandName)
                .thumbnail(thumbnail==null ? null : Constants.CDN_URL.concat(thumbnail))
                .reviewCount(reviewCount)
                .reviewScore(reviewScore)
                .newLowestPrice(newLowestPrice)
                .usedLowestPrice(usedLowestPrice)
                .build();
    }

    public static List<ItemBundleRecommendResponse> of(List<ItemBundleRecommendProjection> list){
        return list
                .stream()
                .map(p -> ItemBundleRecommendResponse.of(p.getId(), p.getName(), p.getBrandName(), p.getThumbnail(), p.getReviewCount(), p.getReviewScore(), p.getNewLowestPrice(), p.getUsedLowestPrice()))
                .collect(Collectors.toList());

    }

}
