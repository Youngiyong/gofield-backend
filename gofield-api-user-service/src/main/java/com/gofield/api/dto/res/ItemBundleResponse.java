package com.gofield.api.dto.res;

import com.gofield.domain.rds.domain.item.EItemClassificationFlag;
import com.gofield.domain.rds.domain.item.projection.ItemBundleImageProjectionResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class ItemBundleResponse {
    private Long id;
    private String name;
    private String brandName;
    private String thumbnail;
    private int reviewCount;
    private Double reviewScore;
    private int newLowestPrice;
    private int usedLowestPrice;
    private int newItemCount;
    private int usedItemCount;
    private List<String> images;
    private List<ItemClassificationResponse> items;

    @Builder
    private ItemBundleResponse(Long id, String name, String brandName, String thumbnail, int reviewCount, Double reviewScore, int newLowestPrice, int usedLowestPrice, int newItemCount, int usedItemCount, List<String> images, List<ItemClassificationResponse> items){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.thumbnail = thumbnail;
        this.reviewCount = reviewCount;
        this.reviewScore = reviewScore;
        this.newLowestPrice = newLowestPrice;
        this.usedLowestPrice = usedLowestPrice;
        this.newItemCount = newItemCount;
        this.usedItemCount = usedItemCount;
        this.images = images;
        this.items = items;
    }

    public static ItemBundleResponse of(ItemBundleImageProjectionResponse projection){
        int newItemCount = 0;
        int usedItemCount = 0;
        List<ItemClassificationResponse> items = ItemClassificationResponse.of(projection.getItems());
        for(ItemClassificationResponse classification: items){
            if(classification.getClassification().equals(EItemClassificationFlag.USED)){
                usedItemCount++;
            } else {
                newItemCount++;
            }
        }

        return ItemBundleResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .brandName(projection.getBrandName())
                .thumbnail(projection.getThumbnail())
                .reviewCount(projection.getReviewCount())
                .reviewScore(projection.getReviewScore())
                .newLowestPrice(projection.getNewLowestPrice())
                .usedLowestPrice(projection.getUsedLowestPrice())
                .newItemCount(newItemCount)
                .usedItemCount(usedItemCount)
                .images(projection.getImages())
                .items(items)
                .build();
    }


}
