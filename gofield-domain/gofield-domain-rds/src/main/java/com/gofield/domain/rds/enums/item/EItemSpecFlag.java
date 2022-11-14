package com.gofield.domain.rds.enums.item;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EItemSpecFlag implements CodeEnum {
    PARALLEL_IMPORT("병행수입", "P"),
    GENUINE( "정품", "G"),
    ETC("기타", "E")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

}
