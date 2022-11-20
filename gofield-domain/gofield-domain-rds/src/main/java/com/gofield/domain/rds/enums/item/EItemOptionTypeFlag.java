package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemOptionTypeFlag implements CodeEnum {
    SINGLE("단독형", "S"),
    COMBINATION("조합형", "C"),
    INPUT("입력형", "I")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
