package com.gofield.domain.rds.enums;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EPlatformFlag implements CodeEnum {
    ANDROID("안드로이드", "A"),
    IOS( "애플", "I");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
