package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ECategoryFlag implements CodeEnum {
    NORMAL("일반", "N"),
    OTHER( "기타", "O");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
