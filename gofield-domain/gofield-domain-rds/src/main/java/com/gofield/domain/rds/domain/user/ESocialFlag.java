package com.gofield.domain.rds.domain.user;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ESocialFlag implements EnumCodeModel {
    KAKAO("카카오", "K"),
    NAVER( "네이버", "N"),
    APPLE( "애플", "A");

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getDescription(){ return description; }

}
