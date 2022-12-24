package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EItemSort implements EnumModel {
    NEWEST("최신순"), OLDEST("오래된순"), HIGHER_PRICE("높은 가격순"), LOWER_PRICE("낮은 가격순")
    ;

    private String description;

    @Override
    public String getKey() {
        return name();
    }
}

