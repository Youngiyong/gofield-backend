package com.gofield.domain.rds.enums.qna;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EQnaStatusFlag implements CodeEnum {
    COMPLETE("완료", "C"),
    RECEIPT( "접수", "R");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
