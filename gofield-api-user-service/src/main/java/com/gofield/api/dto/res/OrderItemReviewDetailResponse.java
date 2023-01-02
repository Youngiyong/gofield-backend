package com.gofield.api.dto.res;

import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.ItemBundleReview;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderItemReviewDetailResponse {
    private Long reviewId;
    private String name;
    private String itemNumber;
    private String optionName;
    private String thumbnail;
    private int price;
    private int qty;
    private Double reviewScore;
    private String description;
    private List<String> images;

    @Builder
    private OrderItemReviewDetailResponse(Long reviewId, String name, String itemNumber,  String optionName, String thumbnail, int price, int qty, Double reviewScore, String description, List<String> images){
        this.reviewId = reviewId;
        this.name = name;
        this.itemNumber = itemNumber;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.qty = qty;
        this.reviewScore = reviewScore;
        this.description = description;
        this.images = images;
    }

    public static OrderItemReviewDetailResponse of(ItemBundleReview itemBundleReview){
        return OrderItemReviewDetailResponse.builder()
                .reviewId(itemBundleReview.getId())
                .name(itemBundleReview.getName())
                .itemNumber(itemBundleReview.getItemNumber())
                .optionName(itemBundleReview.getOptionName())
                .thumbnail(CommonUtils.makeCloudFrontUrl(itemBundleReview.getThumbnail()))
                .price(itemBundleReview.getPrice())
                .qty(itemBundleReview.getQty())
                .reviewScore(itemBundleReview.getReviewScore())
                .description(itemBundleReview.getDescription())
                .images(itemBundleReview.getImages().stream().map(k -> CommonUtils.makeCloudFrontUrl(k.getImage())).collect(Collectors.toList()))
                .build();
    }

    public static List<OrderItemReviewDetailResponse> of(List<ItemBundleReview> list){
        return list
                .stream()
                .map(OrderItemReviewDetailResponse::of)
                .collect(Collectors.toList());
    }

}
