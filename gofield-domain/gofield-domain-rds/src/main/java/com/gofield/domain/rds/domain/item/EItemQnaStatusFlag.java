package com.gofield.domain.rds.domain.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemQnaStatusFlag implements CodeEnum {
    COMPLETE("완료", "C"),
    RECEIPT( "접수", "W");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}