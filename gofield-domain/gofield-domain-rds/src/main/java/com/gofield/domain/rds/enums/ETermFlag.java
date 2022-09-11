package com.gofield.domain.rds.enums;

import com.gofield.common.exception.model.ConvertException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ETermFlag {
    PRIVACY("개인정보", "P"),
    MARKETING("마케팅", "M"),
    LOCATION("위치", "L"),
    SERVICE( "서비스 약관", "S");

    private String description;
    private String code;

    public static ETermFlag ofCode(String code){
        return Arrays.stream(ETermFlag.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertException(String.format("상태코드에 code=[%s]가 존재하지 않습니다.", code)));
    }
}
