package com.gofield.domain.rds.domain.common;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EStatusFlag implements EnumCodeModel {
    ACTIVE("활성", "A"),
    DELETE("탈퇴", "D"),
    WAIT("대기","W"),
    BLACK("블랙", "B"),
    PAUSE( "중지", "P");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
