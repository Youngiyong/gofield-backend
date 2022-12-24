package com.gofield.domain.rds.domain.item.projection;

import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private PaginationResponse page;

    @Builder
    public ItemBundleImageProjectionResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, List<String> images, List<ItemClassificationProjectionResponse> items, PaginationResponse page) {
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
        this.page = page;
    }

    public static ItemBundleImageProjectionResponse of(ItemBundleImageProjection projection,  List<String> images, List<ItemClassificationProjectionResponse> items, PaginationResponse page){
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
                .page(page)
                .build();
    }


}
