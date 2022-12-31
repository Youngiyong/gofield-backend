package com.gofield.api.dto.res;

import com.gofield.api.util.ApiUtil;
import com.gofield.common.model.Constants;
import com.gofield.common.utils.CommonUtils;
import com.gofield.domain.rds.domain.item.ItemBundleReview;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemBundleReviewResponse {

    private Long id;
    private String name;
    private String nickName;
    private String optionName;
    private Integer weight;
    private Integer height;
    private Double reviewScroe;
    private String description;
    private LocalDateTime createDate;
    private List<String> images;

    @Builder
    public ItemBundleReviewResponse(Long id, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScroe, String description, LocalDateTime createDate, List<String> images) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.optionName = optionName;
        this.weight = weight;
        this.height = height;
        this.reviewScroe = reviewScroe;
        this.description = description;
        this.createDate = createDate;
        this.images = images;
    }

    public static ItemBundleReviewResponse of(ItemBundleReview bundleReview){
        return ItemBundleReviewResponse.builder()
                .id(bundleReview.getId())
                .name(bundleReview.getName())
                .nickName(bundleReview.getNickName())
                .optionName(bundleReview.getOptionName())
                .weight(bundleReview.getWeight())
                .height(bundleReview.getHeight())
                .reviewScroe(bundleReview.getReviewScore())
                .description(bundleReview.getDescription())
                .createDate(bundleReview.getCreateDate())
                .images(bundleReview.getImages().isEmpty() ? new ArrayList<>() : bundleReview.getImages().stream().map(k-> CommonUtils.makeCloudFrontUrlResize(k.getImage())).collect(Collectors.toList()))
                .build();
    }

    public static List<ItemBundleReviewResponse> of(List<ItemBundleReview> list){
        return list
                .stream()
                .map(ItemBundleReviewResponse::of)
                .collect(Collectors.toList());
    }

}
