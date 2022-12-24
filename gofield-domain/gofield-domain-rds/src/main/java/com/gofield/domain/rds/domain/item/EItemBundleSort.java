package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EItemBundleSort implements EnumModel {
    RECOMMEND("추천순"), POPULAR("인기순"), NEWEST("최신순"), REVIEW("평점 높은순"), HIGHER_PRICE("높은 가격순"), LOWER_PRICE("낮은 가격순")
    ;

    private String description;
    @Override
    public String getKey() {
        return name();
    }
}
