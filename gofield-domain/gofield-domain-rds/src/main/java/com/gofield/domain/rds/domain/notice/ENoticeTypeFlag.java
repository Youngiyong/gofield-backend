package com.gofield.domain.rds.domain.notice;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ENoticeTypeFlag implements EnumCodeModel {
    NORMAL("일반", "N"),
    EVENT("이벤트", "E"),
    CHECK("점검", "C")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
