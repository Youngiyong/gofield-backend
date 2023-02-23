package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemSpecFlag implements EnumCodeModel {
    BEST("최상급", "B"),
    UPPER("상급", "U"),
    MIDDLE( "중급", "M"),
    LOWER("하급", "L")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
