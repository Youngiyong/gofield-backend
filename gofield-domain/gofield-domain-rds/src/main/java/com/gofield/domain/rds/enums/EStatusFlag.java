package com.gofield.domain.rds.enums;

import com.gofield.common.exception.model.ConvertException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EStatusFlag {
    ACTIVE("활성", "A"),
    DELETE("탈퇴", "D"),
    WAIT("대기","W"),
    PENDING("보류", "H"),
    PAUSE( "중지", "P");

    private String description;
    private String code;

    public static EStatusFlag ofCode(String code){

        return Arrays.stream(EStatusFlag.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertException(String.format("상태코드에 code=[%s]가 존재하지 않습니다.", code)));
    }
}
