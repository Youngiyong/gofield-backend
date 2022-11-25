package com.gofield.domain.rds.enums.order;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelCodeFlag implements CodeEnum {
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
