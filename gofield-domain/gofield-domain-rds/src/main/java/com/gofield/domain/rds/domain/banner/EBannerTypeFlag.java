package com.gofield.domain.rds.domain.banner;

import com.gofield.domain.rds.converter.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EBannerTypeFlag implements CodeEnum {

    MAIN("메인 배너", "M"),
    POPUP("팝업 배너", "P")
    ;

    private String description;
    private String code;

    @Override
    public String getKey() {
        return name();
    }
}
