package com.gofield.domain.rds.domain.common;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EEnvironmentFlag implements EnumCodeModel {
    LOCAL("로컬", "L"),
    DEV( "개발", "D"),
    PROD( "운영", "P");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
