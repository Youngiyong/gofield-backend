package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemSpecFlag implements CodeEnum {
    BEST("최상", "B"),
    UPPER("상", "U"),
    MIDDLE( "중", "M"),
    LOWER("하", "L")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
