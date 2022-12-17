package com.gofield.domain.rds.domain.item;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ECategoryFlag implements EnumCodeModel {
    NORMAL("일반", "N"),
    OTHER( "기타", "O");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
