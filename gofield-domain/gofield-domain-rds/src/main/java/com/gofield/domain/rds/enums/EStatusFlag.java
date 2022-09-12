package com.gofield.domain.rds.enums;

import com.gofield.common.exception.model.ConvertException;
import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EStatusFlag implements CodeEnum {
    ACTIVE("활성", "A"),
    DELETE("탈퇴", "D"),
    WAIT("대기","W"),
    PENDING("보류", "H"),
    PAUSE( "중지", "P");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
