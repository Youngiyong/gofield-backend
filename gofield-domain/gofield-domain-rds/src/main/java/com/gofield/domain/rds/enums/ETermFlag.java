package com.gofield.domain.rds.enums;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ETermFlag implements CodeEnum {
    PRIVACY("개인정보", "P"),
    MARKETING("마케팅", "M"),
    MARKETING_EMAIL("마케팅 이메일", "ME"),
    MARKETING_SMS("마케팅 메시지", "MS"),
    MARKETING_PUSH("마케팅 푸쉬", "MP"),
    LOCATION("위치", "L"),
    SERVICE( "서비스 약관", "S");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
