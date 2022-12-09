package com.gofield.domain.rds.domain.item.projection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ItemBundleReviewScoreProjection {

    private final Long id;
    private final String nickName;
    private final Double reviewScore;

    @QueryProjection
    public ItemBundleReviewScoreProjection(Long id, String nickName, Double reviewScore) {
        this.id = id;
        this.nickName = nickName;
        this.reviewScore = reviewScore;
    }
}
