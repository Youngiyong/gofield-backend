package com.gofield.domain.rds.projections.response;

import com.gofield.domain.rds.projections.ItemBundleImageProjection;
import com.gofield.domain.rds.projections.ItemClassificationProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemBundleImageProjectionResponse {

    private Long id;
    private String name;
    private String brandName;
    private String thumbnail;
    private int reviewCount;
    private Double reviewScore;
    private int newLowestPrice;
    private int usedLowestPrice;

    private List<String> images;

    private List<ItemClassificationProjectionResponse> items;

    @Builder
    public ItemBundleImageProjectionResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, List<String> images, List<ItemClassificationProjectionResponse> items) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
        this.images = images;
        this.items = items;
    }

    public static ItemBundleImageProjectionResponse of(ItemBundleImageProjection projection,  List<String> images, List<ItemClassificationProjectionResponse> items){
        return ItemBundleImageProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .reviewCount(projection.getReviewCount())
                .reviewScore(projection.getReviewScore())
                .newLowestPrice(projection.getNewLowestPrice())
                .usedLowestPrice(projection.getUsedLowestPrice())
                .images(images)
                .items(items)
                .build();
    }
}
