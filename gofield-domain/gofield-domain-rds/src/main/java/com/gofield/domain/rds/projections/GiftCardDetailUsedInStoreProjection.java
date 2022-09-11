package com.gofield.domain.rds.projections;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GiftCardDetailUsedInStoreProjection {

    private final Long id;
    private final int publishAmount;
    private final String giftcardName;
    private final String thumbnail;


    @QueryProjection
    public GiftCardDetailUsedInStoreProjection(Long id, int publishAmount, String giftcardName, String thumbnail) {
        this.id = id;
        this.publishAmount = publishAmount;
        this.giftcardName = giftcardName;
        this.thumbnail = thumbnail;
    }
}
