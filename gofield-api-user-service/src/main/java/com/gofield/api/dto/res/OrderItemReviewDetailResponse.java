package com.gofield.api.dto.res;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gofield.api.util.ApiUtil;
import com.gofield.common.model.Constants;
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
    private List<String> optionName;
    private String thumbnail;
    private int qty;
    private Double reviewScore;
    private String description;

    @Builder
    private OrderItemReviewDetailResponse(Long reviewId, String name, String itemNumber,  List<String> optionName, String thumbnail, int qty, Double reviewScore, String description){
        this.reviewId = reviewId;
        this.name = name;
        this.itemNumber = itemNumber;
        this.optionName = optionName;
        this.thumbnail = thumbnail;
        this.qty = qty;
        this.reviewScore = reviewScore;
        this.description = description;
    }

    public static OrderItemReviewDetailResponse of(Long reviewId, String name, String itemNumber, List<String> optionName, String thumbnail,
                                                   int qty, Double reviewScore, String description){
        return OrderItemReviewDetailResponse.builder()
                .reviewId(reviewId)
                .name(name)
                .itemNumber(itemNumber)
                .optionName(optionName)
                .thumbnail(thumbnail)
                .qty(qty)
                .reviewScore(reviewScore)
                .description(description)
                .build();
    }

    public static List<OrderItemReviewDetailResponse> of(List<ItemBundleReview> list){
        return list
                .stream()
                .map(p -> OrderItemReviewDetailResponse.of(p.getId(), p.getName(), p.getItemNumber(), p.getOptionName()==null ? null : ApiUtil.strToObject(p.getOptionName(), new TypeReference<List<String>>(){}),
                            p.getThumbnail()==null ? null : Constants.CDN_URL.concat(p.getThumbnail()), p.getQty(), p.getReviewScore(), p.getDescription()))
                .collect(Collectors.toList());
    }

}
