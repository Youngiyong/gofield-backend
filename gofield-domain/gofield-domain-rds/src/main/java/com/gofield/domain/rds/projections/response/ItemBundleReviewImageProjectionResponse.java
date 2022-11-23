package com.gofield.domain.rds.projections.response;

import com.gofield.domain.rds.projections.ItemBundleImageProjection;
import com.gofield.domain.rds.projections.ItemBundleReviewImageProjection;
import com.gofield.domain.rds.projections.ItemClassificationProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class ItemBundleReviewImageProjectionResponse {

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
    public ItemBundleReviewImageProjectionResponse(Long id, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScroe, String description, LocalDateTime createDate, List<String> images) {
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

    public static ItemBundleReviewImageProjectionResponse of(ItemBundleReviewImageProjection projection, List<String> images){
        return ItemBundleReviewImageProjectionResponse.builder()
                .id(projection.getId())
                .name(projection.getName())
                .nickName(projection.getNickName())
                .optionName(projection.getOptionName())
                .weight(projection.getWeight())
                .height(projection.getHeight())
                .reviewScroe(projection.getReviewScroe())
                .description(projection.getDescription())
                .createDate(projection.getCreateDate())
                .images(images)
                .build();
    }
}
