package com.gofield.domain.rds.domain.item.projection;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.common.PaginationResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ItemBundleImageProjectionResponse {

    private Long id;
    private String name;
    private String brandName;
    private String thumbnail;
    private String description;
    private int reviewCount;
    private Double reviewScore;
    private int newLowestPrice;
    private int usedLowestPrice;
    private int allItemCount;
    private Long newItemCount;
    private Long usedItemCount;
    private List<String> images;


    @Builder
    public ItemBundleImageProjectionResponse(Long id, String name, String brandName, String thumbnail, String description, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, int allItemCount, Long newItemCount, Long usedItemCount,  List<String> images) {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.description = description;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
        this.allItemCount = allItemCount;
        this.newItemCount = newItemCount;
        this.usedItemCount = usedItemCount;
        this.images = images;
    }

    public static ItemBundleImageProjectionResponse of(ItemBundleImageProjection projection, int allItemCount, Long newItemCount, Long usedItemCount, List<String> images){
        return ItemBundleImageProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .description(projection.getDescription())
                .reviewCount(projection.getReviewCount())
                .reviewScore(projection.getReviewScore())
                .newLowestPrice(projection.getNewLowestPrice())
                .usedLowestPrice(projection.getUsedLowestPrice())
                .allItemCount(allItemCount)
                .newItemCount(newItemCount)
                .usedItemCount(usedItemCount)
                .images(images)
                .build();
    }


}
