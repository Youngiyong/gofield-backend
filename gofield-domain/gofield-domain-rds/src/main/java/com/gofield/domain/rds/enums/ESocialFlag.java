package com.gofield.domain.rds.enums;

import com.gofield.common.exception.model.ConvertException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ESocialFlag {
    KAKAO("카카오", "K"),
    NAVER( "네이버", "N"),
    APPLE( "애플", "A");

    private String description;
    private String code;

    public static ESocialFlag ofCode(String code){

        return Arrays.stream(ESocialFlag.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new ConvertException(String.format("상태코드에 code=[%s]가 존재하지 않습니다.", code)));
    }
}
