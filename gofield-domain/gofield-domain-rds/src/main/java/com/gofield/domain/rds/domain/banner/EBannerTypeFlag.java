package com.gofield.domain.rds.domain.banner;

import com.gofield.common.model.EnumCodeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum EBannerTypeFlag implements EnumCodeModel {

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
