package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemChargeFlag implements CodeEnum {
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
