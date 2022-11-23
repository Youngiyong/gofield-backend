package com.gofield.domain.rds.projections;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
public class ItemBundleReviewImageProjection {

    private final Long id;
    private final String name;
    private final String nickName;
    private final String optionName;
    private final Integer weight;
    private final Integer height;
    private final Double reviewScroe;
    private final String description;
    private final LocalDateTime createDate;
    private final List<String> images;

    @QueryProjection
    public ItemBundleReviewImageProjection(Long id, String name, String nickName, String optionName, Integer weight, Integer height, Double reviewScroe, String description, LocalDateTime createDate, List<String> images) {
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

}
