package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemChargeFlag implements EnumCodeModel {
    FREE("묶음 배송", "B"),
    FIXED("고정 묶음", "F"),
    EACH( "개별 배송", "E");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
