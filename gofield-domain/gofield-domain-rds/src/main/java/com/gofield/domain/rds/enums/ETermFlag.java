package com.gofield.domain.rds.enums;

import com.gofield.common.exception.model.ConvertException;
import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ETermFlag implements CodeEnum {
    PRIVACY("개인정보", "P"),
    MARKETING("마케팅", "M"),
    LOCATION("위치", "L"),
    SERVICE( "서비스 약관", "S");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
