package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderChangeFlagCodeModel implements EnumCodeModel {
    USER("사용자 취소", "101"),
    ADMIN("관리자 취소", "102"),
    SELLER("셀러 취소", "103"),
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
