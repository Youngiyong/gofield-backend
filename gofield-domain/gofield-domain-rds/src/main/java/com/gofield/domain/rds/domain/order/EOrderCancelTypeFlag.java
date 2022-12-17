package com.gofield.domain.rds.domain.order;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EOrderCancelTypeFlag implements EnumCodeModel {
    CANCEL("취소", "C"),
    RECALLED("반품", "R"),
    ;
    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
